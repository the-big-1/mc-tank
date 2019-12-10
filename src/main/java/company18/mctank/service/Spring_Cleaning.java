package company18.mctank.service;

import static java.util.concurrent.TimeUnit.*;

import org.salespointframework.order.OrderManager;
import org.salespointframework.time.BusinessTime;
import java.time.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import company18.mctank.domain.Customer;
import company18.mctank.domain.McTankOrder;
import company18.mctank.repository.CustomerRepository;
//Names are subject to change

class Spring_Cleaning {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final OrderManager orderManager;
	
	public void springCleaning() {
		final Runnable cleaning = new Runnable() { 
			public void run() {
				// TODO needs OrderManager findAll with Iterable
				// needs to be forEach instead of if
				//findAll needs pageable
				for(McTankOrder s:orderManager.findAll(pageable)) {
					if(s.getDateCreated().until(newYear,DAYS)>=100) {
							orderManager.delete(s);
						}
					}
				CustomerRepository customers;
				for(Customer c:customers.findAll()) {
						// registrationDate (Customers) needs parse to local date time
						//if(c.getRegisterDate().until(oneYearLater,DAYS)>=365){customerService.delete(c);}
					}
				}
			};
		};
		final ScheduledFuture<?> cleaningHandle = scheduler.scheduleAtFixedRate(cleaning,initialDelay, 525600, MINUTES);
	}
}
// LocalDateTime newYear is January 1 .0:00 next year 
// initialDelay= now().until(newYear,MINUTES) parsed to long
