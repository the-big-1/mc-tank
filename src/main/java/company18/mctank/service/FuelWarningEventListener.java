package company18.mctank.service;

import company18.mctank.controller.CartController;
import company18.mctank.controller.OverviewController;
import company18.mctank.domain.FuelWarningEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FuelWarningEventListener implements ApplicationListener<FuelWarningEvent> {

	@Autowired
	private CartController cart;

	@Autowired
	private OverviewController overview;

	@Override
	public void onApplicationEvent(FuelWarningEvent event){
		cart.setFuelWarning(event.getFuelWarning());
		//overview.setFuelWarning(event.getFuelWarning());

		System.out.println(cart.getFuelWarning());
	}
}
