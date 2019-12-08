package company18.mctank.initializer;

import java.time.LocalDateTime;

import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.service.ReservationService;

@Component
public class ReservationDataInitializer implements DataInitializer {
	private ReservationService service;

	private static final Logger LOG = LoggerFactory.getLogger(ReservationDataInitializer.class);
	
	public ReservationDataInitializer(ReservationService service) {
		Assert.notNull(service, "ReservationService must not be null.");
		this.service = service;
	}

	@Override
	public void initialize() {
		LOG.info("Reservation data Initializing");
		service.save("McSit", "Party 01", LocalDateTime.now().plusDays(1).plusHours(5), "boss");
		service.save("McSit", "Birthday 01", LocalDateTime.now().plusDays(2).plusHours(4), "boss");
		service.save("McSit", "Birthday 10", LocalDateTime.now().plusDays(3).plusHours(3), "boss");
		service.save("McSit", "Birthday 05", LocalDateTime.now().plusDays(4).plusHours(2), "boss");
		service.save("McSit", "Party 23", LocalDateTime.now().plusDays(5).plusHours(1), "boss");
		
		service.save("McWash", "Tank", LocalDateTime.now().plusDays(5).plusHours(1), "boss");
		service.save("McWash", "Car", LocalDateTime.now().plusDays(4).plusHours(2), "boss");
		service.save("McWash", "Bike", LocalDateTime.now().plusDays(3).plusHours(3), "boss");
		LOG.info("Initializing Reservation data");
	}
}
