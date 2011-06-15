package cl.votainteligente.inspector.shared;

public class NotificationEventParams {

	private NotificationEventType type;
	private String message;
	private Integer duration;

	public static final Integer DURATION_SHORT = 10000;
	public static final Integer DURATION_NORMAL = 15000;
	public static final Integer DURATION_LONG = 20000;
	public static final Integer DURATION_PERMANENT = 0;

	public NotificationEventType getType() {
		return type;
	}

	public void setType(NotificationEventType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
