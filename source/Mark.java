public class Mark extends Vector {
	private int milliseconds;
	private int seconds;
	private int minutes;
	private int hours;
	private boolean afterReset;

	private long timestamp;

	public Mark( double x, double y, long timestamp ) {
		super(x, y);
		this.timestamp = timestamp;
		this.milliseconds = (int) (timestamp % 1000);
		timestamp /= 1000;
		this.seconds = (int) (timestamp % 60);
		timestamp /= 60;
		this.minutes = (int) (timestamp % 60);
		this.hours = (int) ( timestamp / 60 );

		this.afterReset = false;
	}

	public int[] getTime() {
		return new int[] { this.milliseconds, this.seconds, this.minutes, this.hours };
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setAfterReset( boolean afterReset ) {
		this.afterReset = afterReset;
	}

	public boolean isAfterReset() {
		return this.afterReset;
	}
}