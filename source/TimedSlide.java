import java.util.*;

public class TimedSlide extends Slide {
	
	private ArrayList<Mark> firstMatch;
	private boolean wasReset;

	public TimedSlide( MarkConfig markConfig ) {
		super( markConfig );
		this.firstMatch = new ArrayList<Mark>();
		this.wasReset = false;
	}

	@Override
	public void addBug( BugBox bug ) {
		int index = this.bugs.size();
		super.addBug( bug );		
		this.firstMatch.add( null );
	}

	@Override
	public void addMark( Vector newMark ) {
		super.addMark( newMark );

		if( !( newMark instanceof Mark ) ) {
			throw new UnsupportedOperationException("newMark must be an instance of Mark");
		}

		Mark mark = (Mark) newMark;
		if( this.wasReset ) {
			this.wasReset = false;
			mark.setAfterReset( true );
		}
		
		for(int i=0; i<this.bugs.size(); i++) {
			BugBox bug = this.bugs.get(i);
			if( this.markConfig.isEncircled( bug, mark ) ) {
				if( this.firstMatch.get( i ) == null ||
					this.firstMatch.get( i ).getTimestamp() > mark.getTimestamp() ) {
					this.firstMatch.set( i, mark );
				}
			}
		}
	}

	@Override
	public void reset() {
		this.wasReset = true;
	}

	public void hardReset() {
		Collections.fill( this.firstMatch, null );
		this.wasReset = false;
		this.marks.clear();
	}

	@Override
	public boolean[] getScore() {
		boolean[] score = new boolean[ this.bugs.size() ];
		for( int i=this.marks.size()-1; i>=0; i-- ) {
			for( int j=0; j<this.bugs.size(); j++ ) {
				if( score[j] ) continue;

				if( markConfig.isEncircled( this.bugs.get(j), this.marks.get(i) ) ) {
					score[j] = true;
				}
			}

			if( ( (Mark) this.marks.get( i ) ).isAfterReset() ) break;
		}

		return score;
	}

	public int[][] getTimedScore() {
		boolean[] score = this.getScore();
		int[][] timedScore = new int[ this.bugs.size() ][ 4 ];
		for( int i=0; i<this.bugs.size(); i++ ) {
			if( !score[i] || this.firstMatch.get( i ) == null ) {
				timedScore[i][0] = timedScore[i][1] = timedScore[i][2] = timedScore[i][3] = -1;
			} else {
				int[] time = this.firstMatch.get( i ).getTime();
				timedScore[i][0] = time[0];
				timedScore[i][1] = time[1];
				timedScore[i][2] = time[2];
				timedScore[i][3] = time[3]; 
			}
		}
		return timedScore;
	}
}