/**
* Slide
*
* Slide represents a slide that may or may not contain images of codes with bugs
* It stores all the bugs within itself, as well as all the markings done on it
* It has a method to assess how much of the bugs have been marked
*/


import java.util.*;

public class Slide {
	
	protected ArrayList<Vector> marks;
	protected ArrayList<BugBox> bugs;
	protected MarkConfig markConfig;
	
	/**
	* Constructor; requires a MarkConfig to determine type of ellipse
	*
	* @param markConfig 	MarkConfig describing the configuration of elliptical marks
	*/
	public Slide( MarkConfig markConfig ) {
		this.bugs = new ArrayList<BugBox>();
		this.markConfig = markConfig;
		this.marks = new ArrayList<Vector>();
	}
	
	/**
	* Adds a BugBox to the slide
	*
	* @param bug 	the BugBox to be added
	*/
	public void addBug( BugBox bug ) {
		this.bugs.add( bug );
	}
	
	/**
	* Adds a marked position to the slide
	*
	* @param newMark	the mark to be added
	*/
	public void addMark( Vector newMark ) {
		this.marks.add( newMark );
	}
	
	/**
	* Clears all the marked position
	*/
	public void reset() {
		this.marks.clear();
	}
	
	/**
	* Assesses which bugs have been marked
	* Returns an array of booleans wherein the i-th boolean tells if the i-th bug have been marked 
	*
	* @return	boolean[]
	*/
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