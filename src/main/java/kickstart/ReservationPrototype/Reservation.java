package kickstart.ReservationPrototype;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reservation {
	@Id @GeneratedValue
	private long id;
	
	private String name;
	private Date date;
	
	public Reservation() {}
	
	public Reservation(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Date getDate() {
		return this.date;
	}
}
