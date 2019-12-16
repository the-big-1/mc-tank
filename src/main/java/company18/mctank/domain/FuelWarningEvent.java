package company18.mctank.domain;

import org.springframework.context.ApplicationEvent;

public class FuelWarningEvent extends ApplicationEvent {
	private Boolean fuelWarning;

	public FuelWarningEvent(Object source, boolean fuelWarning){
		super(source);
		this.fuelWarning = fuelWarning;
	}

	public boolean getFuelWarning(){
		return fuelWarning;
	}
}


