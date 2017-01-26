import java.io.*;

public class Checker {
	
	public static void main(String[] args) throws IOException {
		if( args.length != 2 ) {
			System.out.println("Usage: java Checker <input-directory-path> <output-directory-path>");
		}
		
		String inputPath = args[0];
		String outputPath = args[1];
		
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
				
				boolean success = processFile( br, fileName );
				if( success ) {
					// output
				}
			}
			
		} else if( inputDirectory.exists() && !inputDirectory.isDirectory() ) {
			System.err.println("Error: " + inputPath + " is not a directory!");
		} else {
			System.err.println("Error: " + inputPath + " does not exist!");
		}
	}
	
	public static boolean processFile( BufferedReader br, String fileName ) throws IOException {
		String line = br.readLine();
		String[] tokens = line.split(" ");
		
		String username = tokens[1];
		line = br.readLine();
		if( !"Username:".equals( tokens[0] ) || !"action_type,timestamp,slide,x,y". equals( line ) ) {
			System.err.println("Error in " + fileName + ": invalid format!" );
			return false;
		}
		
		br.readLine();
		
		line = br.readLine();
		while( line != null ) {
			tokens = line.split(",");
			
			// process further
			
			line = br.readLine();
		}
		
		return true;
	}
	
}