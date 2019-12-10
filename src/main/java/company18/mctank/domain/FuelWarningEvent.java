package company18.mctank.domain;

import org.springframework.context.ApplicationEvent;

public class FuelWarningEvent extends ApplicationEvent {
	private String message;

	public FuelWarningEvent(Object source, String message){
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}


