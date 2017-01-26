/**
* Vector
* 
* Vector represents position in 2D plane.
* Has a horizontal (x) and vertical (y) dimension.
*/

public class Vector {
	private double x;
	private double y;
	
	/**
	* Constructor; requires x and y position
	* 
	* @param x 	horizontal position
	* @param y 	vertical position
	*/
	public Vector( double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	/**
	* Returns the vector describing the difference between this and another vector
	*
	* @param that	the other vector
	*
	* @return 		Vector
	*/
	public Vector minus( Vector that ) {
		double resultantX = this.getX() - that.getX();
		double resultantY = this.getY() - that.getY();
		return new Vector( resultantX, resultantY );
	}
	
	public String toString() {
		return "< " + x + ", " + y + " >"; 
	}
}