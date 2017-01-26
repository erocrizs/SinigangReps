import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

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
				
				// check validity
				// process
				// output
			}
			
		} else if( inputDirectory.exists() && !inputDirectory.isDirectory() ) {
			System.err.println("Error: " + inputPath + " is not a directory!");
		} else {
			System.err.println("Error: " + inputPath + " does not exist!");
		}
	}
	
}

class Chooser extends 