public class Vector {
	private double x;
	private double y;
	
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
	
	public Vector minus( Vector that ) {
		double resultantX = this.getX() - that.getX();
		double resultantY = this.getY() - that.getY();
		return new Vector( resultantX, resultantY );
	}
	
	public String toString() {
		return "< " + x + ", " + y + " >"; 
	}
}