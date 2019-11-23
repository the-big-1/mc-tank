package company18.mctank.config;

import java.time.LocalDateTime;

import org.salespointframework.core.DataInitializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.service.ReservationService;

@Component
public class ReservationDataInitializer implements DataInitializer {
	private ReservationService service;
	
	public ReservationDataInitializer(ReservationService service) {
		Assert.notNull(service, "ReservationService must not be null.");
		this.service = service;
	}

	@Override
	public void initialize() {
		service.save("McSit", "a", LocalDateTime.of(2019, 12, 14, 10, 50));
		service.save("McSit", "b", LocalDateTime.of(2019, 12, 13, 11, 30));
		service.save("McSit", "c", LocalDateTime.of(2019, 12, 15, 12, 25));
		service.save("McSit", "d", LocalDateTime.of(2019, 12, 11, 13, 20));
		service.save("McSit", "e", LocalDateTime.of(2019, 12, 10, 14, 10));
		
		service.save("McWash", "f", LocalDateTime.of(2019, 12, 14, 10, 50));
		service.save("McWash", "g", LocalDateTime.of(2019, 12, 13, 11, 30));
		service.save("McWash", "h", LocalDateTime.of(2019, 12, 15, 12, 25));
		service.save("McWash", "i", LocalDateTime.of(2019, 12, 11, 13, 20));
		service.save("McWash", "j", LocalDateTime.of(2019, 12, 10, 14, 10));
	}
}
