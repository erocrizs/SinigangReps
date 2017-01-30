public class Mark extends Vector {
	private int milliseconds;
	private int seconds;
	private int minutes;
	private int hours;

	private long timestamp;

	public Mark( double x, double y, long timestamp ) {
		super(x, y);
		this.timestamp = timestamp;
		this.milliseconds = (int) (time % 1000);
		time /= 1000;
		this.seconds = (int) (time % 60);
		time /= 60;
		this.minutes = (int) (time % 60);
		this.hours = (int) ( time / 60 );
	}

	public int[] getTime() {
		return new int[] { this.milliseconds, this.seconds, this.minutes, this.hours };
	}

	public long getTimestamp() {
		return this.timestamp;
	}
}