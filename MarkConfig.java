public class MarkConfig {
	
	private double a;
	private double b;
	
	public MarkConfig( double horizontalAxis, double verticalAxis ) {
		this.a = horizontalAxis / 2.0;
		this.b = verticalAxis / 2.0;
	}
	
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