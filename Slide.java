import java.util.*;

public class Slide {
	
	private ArrayList<Vector> marks;
	private ArrayList<BugBox> bugs;
	private MarkConfig markConfig;
	
	public Slide( MarkConfig markConfig ) {
		this.bugs = new ArrayList<BugBox>();
		this.markConfig = markConfig;
		this.marks = new ArrayList<Vector>();
	}
	
	public void addBug( BugBox bug ) {
		this.bugs.add( bug );
	}
	
	public void addMark( Vector newMark ) {
		this.marks.add( newMark );
	}
	
	public void reset() {
		this.marks.clear();
	}
	
	public boolean[] getScore() {
		
		boolean[] score = new boolean[ this.bugs.size() ];
		for( int i=0; i<this.bugs.size(); i++ ) {
			for( Vector mark: this.marks ) {
				if( markConfig.isEncircled( bugs.get( i ), mark ) ) {
					score[i] = true;
					break;
				}
			}
		}
		return score;
	}
	
}