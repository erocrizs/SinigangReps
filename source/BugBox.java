/**
* BugBox
* 
* BugBox represents the bounding rectangle of a bug in the slides.
* Stores the four corners of the bounding box.
*/
public class BugBox {
	
	private Vector topLeft;
	private Vector topRight;
	private Vector botLeft;
	private Vector botRight;
	private double width;
	private double height;
	
	/**
	* Constructor; requires two vectors representing the top-left and bottom-right corner
	* Note: this is made with the knowledge that the y position increases from top downwards
	* 
	* @param topLeft 	the top-left corner
	* @param botRight 	the bottom-right corner
	*/
	public BugBox( Vector topLeft, Vector botRight ) {
		this.topLeft = topLeft;
		this.botRight = botRight;
		this.topRight = new Vector( botRight.getX(), topLeft.getY() );
		this.botLeft = new Vector( topLeft.getX(), botRight.getY() );
		
		this.width = Math.abs( topLeft.getX() - botRight.getX() );
		this.height = Math.abs( botRight.getY() - topLeft.getY() );
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public Vector getTopLeft() {
		return this.topLeft;
	}
	
	public Vector getTopRight() {
		return this.topRight;
	}
	
	public Vector getBotLeft() {
		return this.botLeft;
	}
	
	public Vector getBotRight() {
		return this.botRight;
	}
	
	/**
	* Returns an array of vectors containing the four corners of the bounding box
	* 
	* @return 	Vector[]
	*/
	public Vector[] getCorners() {
		return new Vector[] { this.topLeft, this.topRight, this.botLeft, this.botRight };
	}
	
	public String toString() {
		return "{ " + topLeft.toString() + ", " + botRight + " }";
	}
}