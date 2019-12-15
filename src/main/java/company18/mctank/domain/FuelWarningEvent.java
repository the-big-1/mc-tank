package company18.mctank.domain;

import org.springframework.context.ApplicationEvent;

public class FuelWarningEvent extends ApplicationEvent {
	private String message;

	public FuelWarningEvent(Object source){
		super(source);
	}
}


