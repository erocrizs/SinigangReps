import java.util.*;
import java.io.*;
import java.text.*;

public class Checker {
	
	/**
	* Main method of the app.
	* Takes care of taking inputs from the command line, managing the file system, and writing the output file
	*/
	public static void main(String[] args) throws IOException {
		// Instantiate a scanner for taking user input
		Scanner in = new Scanner(System.in);
		
		System.out.print("Config File Path (no spaces): ");
		String configPath = in.next();
		
		// Defines the MarkConfig to be used in the entire program
		MarkConfig markConfig = new MarkConfig( 150, 72 );

		// Create the DataModel
		DataModel model = createDataModel( configPath, markConfig );
		if( model == null ) {
			// End program if model creation fails
			return;
		}
		
		System.out.print("Input Directory Path (no spaces): ");
		String inputPath = in.next();

		System.out.print("Output Directory Path (no spaces): ");
		String outputPath = in.next();
		
		File inputDirectory = new File( inputPath );	// the input directory
		if( inputDirectory.exists() && inputDirectory.isDirectory() ) {
			// If the directory exists, process all the valid CSV logs inside it

			File[] inputFiles = inputDirectory.listFiles();
			for( File file: inputFiles ) {
				if( file.isDirectory() ) continue;		// skip subdirectories
				
				String fileName = file.getName();
				if( fileName.length() < 4) continue;
				String fileExt = fileName.substring( fileName.length() - 4 ).toLowerCase();
				if( !".csv".equals( fileExt ) ) continue;	// skip invalid file types
				
				// Instantiate buffered reader to read the files
				FileReader fr = new FileReader( file );
				BufferedReader br = new BufferedReader( fr );

				// process the file and get the username
				String username = processFile( br, fileName, model );

				if( username != null ) {
					// if the processing is successful, output a file to the specified directory

					File outputDirectory = new File( outputPath );
					if( !outputDirectory.exists() || !outputDirectory.isDirectory() ) {
						// Create the directory if it does not exist
						outputDirectory.mkdirs();
					}

					// Create the output file if it does not exist
					String outputFileName = fileName.substring( 0, fileName.length()-4 ) + ".out.csv";
					File outputFile = new File( outputPath + File.separator + outputFileName );
					if( !outputFile.exists() ) {
						outputFile.createNewFile();
					}

					// Instantiate buffered writer to write on the file
					FileWriter fw = new FileWriter( outputFile );
					BufferedWriter bw = new BufferedWriter( fw );
					bw.write( "Username: " + username + "\n" );			// customary first liner
					bw.append( "slide,timeBug1Found,timeBug2Found,...\n" );		// format description of the CSV

					int[][][] scores = ( (TimedDataModel) model ).getTimedScores();
					for(int i=0; i<scores.length; i++) {
						bw.append( i+"" );
						for(int j=0; j<scores[i].length; j++) {
							if( scores[i][j][3] != -1 )
								bw.append( "," + scores[i][j][3] + ":" + scores[i][j][2] + ":" + scores[i][j][1] + ":" + scores[i][j][0] );
							else
								bw.append(",------------");
						}
						bw.append( "\n" );
					}
					bw.close(); // finish writing
				}
				
				model.resetModel(); // reset the model
			}
			
		} else if( inputDirectory.exists() && !inputDirectory.isDirectory() ) {
			System.err.println("Error: " + inputPath + " is not a directory!");
		} else {
			System.err.println("Error: " + inputPath + " does not exist!");
		}
	}
	
	/**
	* Processes a CSV log file and simulates the inputs on the model
	* Returns the username of the log if successful; otherwise, null 
	*
	* @param br 		the buffered reader that reads a valid CSV log
	* @param fileName 	the name of the CSV log
	* @param model 		the data body
	*
	* @return 			String / null
	*
	* @throws			IOException
	*/
	private static String processFile( BufferedReader br, String fileName, DataModel model ) throws IOException {
		String line = br.readLine();
		String[] tokens = line.split(" ");
		
		String username = tokens[1];
		line = br.readLine();
		if( !"Username:".equals( tokens[0] ) || !"action_type,timestamp,slide,x,y". equals( line ) ) {
			System.err.println("Error in " + fileName + ": invalid format!" );
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
		
		line = br.readLine();
		while( line != null ) {
			tokens = line.split(",");

			Date timestamp = new Date();
			try {
				timestamp = formatter.parse( tokens[1] );
			} catch( Exception e ) {}
			long milliseconds = timestamp.getTime();
			
			switch( tokens[0] ) {
				case "Next":
					( (TimedDataModel) model).next( milliseconds );
					break;
				case "Previous":
					model.previous();
					break;
				case "Reset":
					model.reset();
					break;
				case "Mark":
					( (TimedDataModel) model ).addMark( new Vector( Double.parseDouble( tokens[3] ), Double.parseDouble( tokens[4] ) ), milliseconds );
					break;
			}
			
			line = br.readLine();
		}
		
		return username;
	}
	
	/**
	* Creates a data model based on the config file specified by the input pathname
	* Returns the data model if successful; otherwise, it returns null
	*
	* @param pathName 	path name of the config file
	* @param markConfig configuration of the marks
	* 
	* @return 			DataModel / null
	*
	* @throws			IOException
	*/
	private static DataModel createDataModel( String pathName, MarkConfig markConfig ) throws IOException {
		File configFile = new File( pathName );
		
		if( configFile.exists() && configFile.isFile() ) {
			FileReader fr = new FileReader( configFile );
			BufferedReader br = new BufferedReader( fr );
			DataModel model = new TimedDataModel( br, markConfig );
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