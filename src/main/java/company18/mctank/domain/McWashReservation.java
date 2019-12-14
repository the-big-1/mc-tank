package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class McWashReservation extends Reservation {
	@SuppressWarnings("unused")
	private McWashReservation() {
		super();
	}
	
	public McWashReservation(String name, LocalDateTime date, String username) {
		super(name, date, username);
	}

	@Override
	public String getMcPoint() {
		return "McWash";
	}

}
