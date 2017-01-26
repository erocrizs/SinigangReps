import java.util.*;
import java.io.*;

public class Checker {
	
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Config File Path (no spaces): ");
		String configPath = in.next();
		
		MarkConfig markConfig = new MarkConfig( 150, 72 );
		DataModel model = createDataModel( configPath, markConfig );
		
		if( model == null ) {
			return;
		}
		
		System.out.print("Input Directory Path (no spaces): ");
		String inputPath = in.next();

		System.out.print("Output Directory Path (no spaces): ");
		String outputPath = in.next();
		
		File inputDirectory = new File( inputPath );
		
		if( inputDirectory.exists() && inputDirectory.isDirectory() ) {
			
			File[] inputFiles = inputDirectory.listFiles();
			for( File file: inputFiles ) {
				if( file.isDirectory() ) continue;
				
				String fileName = file.getName();
				if( fileName.length() < 4) continue;
				
				String fileExt = fileName.substring( fileName.length() - 4 ).toLowerCase();
				if( !".csv".equals( fileExt ) ) continue;
				
				FileReader fr = new FileReader( file );
				BufferedReader br = new BufferedReader( fr );
				String username = processFile( br, fileName, model );

				if( username != null ) {
					File outputDirectory = new File( outputPath );
					if( !outputDirectory.exists() || !outputDirectory.isDirectory() ) {
						outputDirectory.mkdirs();
					}

					String outputFileName = fileName.substring( 0, fileName.length()-4 ) + ".out.csv";
					File outputFile = new File( outputPath + File.separator + outputFileName );
					if( !outputFile.exists() ) {
						outputFile.createNewFile();
					}

					FileWriter fw = new FileWriter( outputFile );
					BufferedWriter bw = new BufferedWriter( fw );
					bw.append( "Username: " + username + "\n" );
					bw.append( "slide,foundBug1,foundBug2,...\n" );

					boolean[][] scores = model.getScores();
					for(int i=0; i<scores.length; i++) {
						bw.append( i+"" );
						for(int j=0; j<scores[i].length; j++) {
							bw.append( "," + scores[i][j] );
						}
						bw.append( "\n" );
					}
					bw.close();
				}
				
				model.resetModel();
			}
			
		} else if( inputDirectory.exists() && !inputDirectory.isDirectory() ) {
			System.err.println("Error: " + inputPath + " is not a directory!");
		} else {
			System.err.println("Error: " + inputPath + " does not exist!");
		}
	}
	
	public static String processFile( BufferedReader br, String fileName, DataModel model ) throws IOException {
		String line = br.readLine();
		String[] tokens = line.split(" ");
		
		String username = tokens[1];
		line = br.readLine();
		if( !"Username:".equals( tokens[0] ) || !"action_type,timestamp,slide,x,y". equals( line ) ) {
			System.err.println("Error in " + fileName + ": invalid format!" );
			return null;
		}
		
		line = br.readLine();
		while( line != null ) {
			tokens = line.split(",");
			
			switch( tokens[0] ) {
				case "Next":
					model.next();
					break;
				case "Previous":
					model.previous();
					break;
				case "Reset":
					model.reset();
					break;
				case "Mark":
					model.addMark( new Vector( Double.parseDouble( tokens[3] ), Double.parseDouble( tokens[4] ) ) );
					break;
			}
			
			line = br.readLine();
		}
		
		return username;
	}
	
	public static DataModel createDataModel( String pathName, MarkConfig markConfig ) throws IOException {
		File configFile = new File( pathName );
		
		if( configFile.exists() && configFile.isFile() ) {
			FileReader fr = new FileReader( configFile );
			BufferedReader br = new BufferedReader( fr );
			DataModel model = new DataModel( br, markConfig );
			if( !model.isExtractionSuccess() ) {
				System.err.println("Error in " + pathName + ": invalid format!" );
				return null;
			}
			return model;
		} else if( configFile.exists() && !configFile.isFile() ) {
			System.err.println("Error: " + pathName + " invalid path!");
		} else {
			System.err.println("Error: " + pathName + " does not exist!");
		}
		
		return null;
	}
	
}