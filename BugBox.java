public class BugBox {
	
	private Vector topLeft;
	private Vector topRight;
	private Vector botLeft;
	private Vector botRight;
	private double width;
	private double height;
	
	public BugBox( Vector topLeft, Vector botRight ) {
		this.topLeft = topLeft;
		this.botRight = botRight;
		this.topRight = new Vector( botRight.getX(), topLeft.getY() );
		this.botLeft = new Vector( topLeft.getX(), botRight.getY() );
		
		this.width = Math.abs( topLeft.getX() - botRight.getX() );
		this.height = Math.abs( botRight.getY() - topLeft.getY() );
	}
	
	public Vector getWidth() {
		return this.width;
	}
	
	public Vector getHeight() {
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
	
	public Vector[] getCorners() {
		return new Vector[] { this.topLeft, this.topRight, this.botLeft, this.botRight };
	}
	
}