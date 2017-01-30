import java.io.*;
import java.util.*;

public class TimedDataModel extends DataModel {
	
	protected long[] firstTimeSeen;

	public TimedDataModel( BufferedReader details, MarkConfig markConfig ) throws IOException {
		super( details, markConfig );
		this.firstTimeSeen = new long[ this.slideCount ];

		Arrays.fill( this.firstTimeSeen, -1 );
	}

	@Override
	protected void extractDetail( BufferedReader br ) throws IOException {
		String line = br.readLine();
		if( "--config--".equals( line ) ) {
			line = br.readLine();
			String[] tokens = line.split(" ");
			
			this.slideCount = Integer.parseInt( tokens[0] );
			this.slides = new Slide[ this.slideCount ];
			for( int i=0; i<this.slideCount; i++ ) {
				this.slides[i] = new TimedSlide( markConfig );
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

	public void next( long milliseconds ) {
		super.next();
		if( this.firstTimeSeen[ this.currentSlide ] == -1 ) {
			this.firstTimeSeen[ this.currentSlide ] = milliseconds;
		}
	}

	public void addMark( Vector mark, long milliseconds ) {
		long timeSinceFirst = milliseconds - this.firstTimeSeen[ this.currentSlide ];
		Mark converted = new Mark( mark.getX(), mark.getY(), timeSinceFirst );
		this.slides[ this.currentSlide ].addMark( converted );
	}

	@Override
	public void resetModel() {
		this.currentSlide = 0;
		for( Slide slide: this.slides ) {
			( (TimedSlide) slide ).hardReset();
		}
		Arrays.fill( this.firstTimeSeen, -1 );
	}

	public int[][][] getTimedScores() {
		int[][][] timedScores = new int[ this.slideCount ][0][0];
		for( int i=0; i<this.slideCount; i++ ) {
			timedScores[i] = ( (TimedSlide) this.slides[i] ).getTimedScore();
		}
		return timedScores;
	}

}