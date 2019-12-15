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
		if (cart.getFuelWarning() == false) {
			cart.setFuelWarning(true);
		}
		else{
			cart.setFuelWarning(false);
		}


		//overview.setFuelWarning(true);


		//call the controller and view a sweetallert with the fuelwarning
		//use the boolean
		//both on manager overview and cart
		//important: reset bool to false after refilling the fuel depot !!!!!
	}
}
