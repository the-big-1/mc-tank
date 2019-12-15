package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

/**
 * Entity representing McSit reservation. Extends {@link Reservation}.
 * @author CS
 */
@Entity
public class McSitReservation extends Reservation {
	@SuppressWarnings("unused")
	private McSitReservation() {
		super();
	}

	public McSitReservation(String name, LocalDateTime date, String username) {
		super(name, date, username);
	}

	@Override
	public String getMcPoint() {
		return "McSit";
	}
}
