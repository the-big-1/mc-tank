package company18.mctank.initializer;

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
		service.save("McSit", "a", LocalDateTime.now().plusDays(1).plusHours(5));
		service.save("McSit", "b", LocalDateTime.now().plusDays(2).plusHours(4));
		service.save("McSit", "c", LocalDateTime.now().plusDays(3).plusHours(3));
		service.save("McSit", "d", LocalDateTime.now().plusDays(4).plusHours(2));
		service.save("McSit", "e", LocalDateTime.now().plusDays(5).plusHours(1));
		
		service.save("McWash", "f", LocalDateTime.now().plusDays(5).plusHours(1));
		service.save("McWash", "g", LocalDateTime.now().plusDays(4).plusHours(2));
		service.save("McWash", "h", LocalDateTime.now().plusDays(3).plusHours(3));
		service.save("McWash", "i", LocalDateTime.now().plusDays(2).plusHours(4));
		service.save("McWash", "j", LocalDateTime.now().plusDays(1).plusHours(5));
	}
}
