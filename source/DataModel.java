/**
* DataModel
*
* DataModel represents the unified body of data being kept track of this program 
* Contains all the slides, the current slide being processed, and even the configuration of the marks
*/
import java.io.*;

public class DataModel {
	
	private Slide[] slides;
	private MarkConfig markConfig;
	private int currentSlide;
	private int slideCount;
	private boolean success;
	
	/**
	* Constructor; requires a buffered reader that reads the specified config file, and the marking configuration
	*
	* @param details	buffered reader that reads the config file containing the details of the slideset
	* @param markConfig configuration of the marks
	*
	* @throws			IOException
	*/
	public DataModel( BufferedReader details, MarkConfig markConfig ) throws IOException {
		this.markConfig = markConfig;
		this.success = false;
		this.extractDetail( details );
		this.currentSlide = 0;
	}
	
	/**
	* Reads the information from the buffered reader that was inputted on the constructor
	* 
	* @throws IOException
	*/
	private void extractDetail( BufferedReader br ) throws IOException {
		String line = br.readLine();
		if( "--config--".equals( line ) ) {
			line = br.readLine();
			String[] tokens = line.split(" ");
			
			this.slideCount = Integer.parseInt( tokens[0] );
			this.slides = new Slide[ this.slideCount ];
			for( int i=0; i<this.slideCount; i++ ) {
				this.slides[i] = new Slide( markConfig );
			}
			
			int n = Integer.parseInt( tokens[1] );
			while( n-->0 ) {
				line = br.readLine();
				tokens = line.split(" ");
				
				int i = Integer.parseInt( tokens[0] );
				int m = Integer.parseInt( tokens[1] );
				
				while( m-->0 ) {
					line = br.readLine();
					tokens = line.split(" ");
					
					double x1, y1, x2, y2;
					x1 = Double.parseDouble( tokens[0] );
					y1 = Double.parseDouble( tokens[1] );
					x2 = Double.parseDouble( tokens[2] );
					y2 = Double.parseDouble( tokens[3] );
					
					Vector topLeft = new Vector( x1, y1 );
					Vector botRight = new Vector( x2, y2 );
					
					BugBox bug = new BugBox( topLeft, botRight );
					
					this.slides[ i ].addBug( bug );
				}
			}
			
			this.success = true;
		}
	}
	
	public int getCurrentSlideIndex() {
		return currentSlide;
	}

	public void next() {
		if( this.currentSlide+1 < this.slideCount )
			this.currentSlide++;
	}
	
	public void previous() {
		if( this.currentSlide-1 >=0 )
			this.currentSlide--;
	}
	
	public void reset() {
		this.slides[ currentSlide ].reset();
	}
	
	public void addMark( Vector mark ) {
		this.slides[ currentSlide ].addMark( mark );
	}
	
	/**
	* Gets the scores (i.e. which bugs have been marked) of each slide
	* Returns a jagged 2D array
	* 
	* @return 	boolean[][]
	*/
	public boolean[][] getScores() {
		boolean[][] scores = new boolean[ this.slideCount ][ 0 ];
		for( int i=0; i < this.slideCount; i++ ) {
			scores[i] = this.slides[i].getScore();
		}
		return scores;
	}
	
	/**
	* Reset the entire model to allow it to be reused
	*/
	public void resetModel() {
		this.currentSlide = 0;
		for( Slide slide: this.slides ) {
			slide.reset();
		}
	}
	
	/**
	* Returns true if the details have been successfully extracted from the config file
	*
	* @return 	boolean
	*/
	public boolean isExtractionSuccess() {
		return this.success;
	}
}