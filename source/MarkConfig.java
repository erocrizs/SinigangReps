/**
* MarkConfig
* 
* MarkConfig stores the configuration of the elliptical coverage of marks
* Specifically, it stores half the length of the major axis (a) and minor axis (b) of an ellipse
* It can check if a BugBox falls strictly within the ellipse centered on a given point
*/

public class MarkConfig {
	
	private double a;
	private double b;
	
	/**
	* Constructor; Requires the full length of the horizontal axis and vertical axis
	*
	* @param horizontalAxis		horizontal axis of the ellipse
	* @param verticalAxis			vertical axis of the ellipse
	*/
	public MarkConfig( double horizontalAxis, double verticalAxis ) {
		this.a = horizontalAxis / 2.0;
		this.b = verticalAxis / 2.0;
	}
	
	/**
	* Returns true if all four corners of BugBox falls strictly inside the ellipse centered on the given mark
	*
	* @param bug 	the bounding rectangle of the bug
	* @param mark 	the center of the ellipse
	*
	* @return 		boolean
	*/
	public boolean isEncircled( BugBox bug, Vector mark ) {
		boolean encircled = true;
		Vector[] corners = bug.getCorners();
		
		for( Vector corner: corners ) {
			if( !this.collide( mark, corner ) ) {
				encircled = false;
				break;
			}
		}
		
		return encircled;
	}
	
	/**
	* Returns true if the given point falls strictly inside the ellipse centered on the given mark
	* 
	* @param ellipseCenter		center of the ellipse
	* @param point				point to be tested
	*
	* @return 					boolean
	*/
	private boolean collide( Vector ellipseCenter, Vector point ) {
		Vector difference = point.minus( ellipseCenter );
		
		double firstTermNumerator = difference.getX() * difference.getX();
		double firstTermDenominator = a * a;
		
		double secondTermNumerator = difference.getY() * difference.getY();
		double secondTermDenominator = b * b;
		
		double firstTerm = firstTermNumerator / firstTermDenominator;
		double secondTerm = secondTermNumerator / secondTermDenominator;
		double eval = firstTerm + secondTerm;
		
		return eval <= 1;
	}
	
}