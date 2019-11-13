package kickstart.ReservationPrototype;

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
		GregorianCalendar date = new GregorianCalendar();
		date.set(2020, 6, 12, 13, 10);
		repo.save(new Reservation("1234", date.getTime()));
		date.set(2019, 7, 22, 23, 15);
		repo.save(new Reservation("5678", date.getTime()));
	}
	
	
}
