package kickstart.ReservationPrototype;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import org.salespointframework.core.DataInitializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ReservationDataInitializer implements DataInitializer {
	private ReservationRepository repo;
	
	public ReservationDataInitializer(ReservationRepository repo) {
		Assert.notNull(repo, "ReservationRepository must not be null.");
		this.repo = repo;
	}

	@Override
	public void initialize() {
		repo.save(new Reservation("Max", LocalDateTime.of(2019,12,4,13,12)));
		repo.save(new Reservation("Mustermann", LocalDateTime.of(2019,12,6,15,15)));
	}
	
	
}
