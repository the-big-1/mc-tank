package company18.mctank.service;

import company18.mctank.domain.FuelWarningEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FuelWarningEventPublisher {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RefillInventoryService service;

	public void publishWarning(final String message){
		FuelWarningEvent event = new FuelWarningEvent(this, message);
		publisher.publishEvent(event);
	}

	public void checkStock(){
		if(service.getFuelAmountBenzin() < 10.000 || service.getFuelAmountDiesel() < 10.000){
			publishWarning("Stock runs low.");
		}

		//service functions returns NullPointerException don't know exactly why because tests work
		//problem with products
		//goes probably back to initializer

	}
}
