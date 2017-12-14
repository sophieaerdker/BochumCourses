package integrationMethodSophie3;

public class IntegrationEvent {

	private int value;
	private boolean finished;

	public IntegrationEvent(int value, boolean finished) {
		super();
		this.value = value;
		this.finished = finished;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
