import java.io.*;

public class DataModel {
	
	private Slide[] slides;
	private MarkConfig markConfig;
	private int currentSlide;
	private int slideCount;
	
	public DataModel( File details, MarkConfig markConfig ) throws IOException {
		this.markConfig = markConfig;
		this.extractDetail( details );
		this.currentSlide = 0;
	}
	
	private extractDetail( File details ) throws IOException {
		// TODO
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
}