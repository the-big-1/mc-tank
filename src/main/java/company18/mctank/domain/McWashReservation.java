package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

/**
 * Entity representing McWash reservation. Extends {@link Reservation}.
 * @author CS
 */
@Entity
public class McWashReservation extends Reservation {
	@SuppressWarnings("unused")
	private McWashReservation() {
		super();
	}
	
	public McWashReservation(String name, LocalDateTime date) {
		super(name, date);
	}

}
