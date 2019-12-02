package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class McSitReservation extends Reservation {
	@SuppressWarnings("unused")
	private McSitReservation() {
		super();
	}

	public McSitReservation(String name, LocalDateTime date) {
		super(name, date);
	}

}
