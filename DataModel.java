import java.io.*;

public class DataModel {
	
	private Slide[] slides;
	private MarkConfig markConfig;
	private int currentSlide;
	private int slideCount;
	private boolean success;
	
	public DataModel( BufferedReader details, MarkConfig markConfig ) throws IOException {
		this.markConfig = markConfig;
		this.success = false;
		this.extractDetail( details );
		this.currentSlide = 0;
	}
	
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
		this.currentSlide++;
	}
	
	public void previous() {
		this.currentSlide--;
	}
	
	public void reset() {
		this.slides[ currentSlide ].reset();
	}
	
	public void addMark( Vector mark ) {
		this.slides[ currentSlide ].addMark( mark );
	}
	
	public boolean[][] getScores() {
		boolean[][] scores = new boolean[ this.slideCount ][ 0 ];
		for( int i=0; i < this.slideCount; i++ ) {
			scores[i] = this.slides[i].getScore();
		}
		return scores;
	}
	
	public void resetModel() {
		this.currentSlide = 0;
		for( Slide slide: this.slides ) {
			slide.reset();
		}
	}
	
	public boolean isExtractionSuccess() {
		return this.success;
	}
}