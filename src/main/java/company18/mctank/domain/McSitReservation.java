package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

/**
 * Entity representing McSit reservation. Extends {@link Reservation}.
 * @author CS
 */
@Entity
public class McSitReservation extends Reservation {
	int count;
	
	@SuppressWarnings("unused")
	private McSitReservation() {
		super();
	}

	public McSitReservation(String name, LocalDateTime date, String username, int personCount){
		super(name, date, username);
		this.count = personCount;
	}

	@Override
	public String getMcPoint() {
		return "McSit";
	}
	
	public int getCount() {
		return this.count;
	}
}
