package company18.mctank.service;

import company18.mctank.domain.FuelWarningEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FuelWarningEventPublisher {

	@Autowired
	private ApplicationEventPublisher publisher;

	public void publishWarning(final String message){
		FuelWarningEvent event = new FuelWarningEvent(this, message);
		publisher.publishEvent(event);
	}
}
