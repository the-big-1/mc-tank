package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reservation {
	@Id @GeneratedValue
	private long id;
	
	private String name;
	private LocalDateTime date;
	
	public Reservation() {}
	
	public Reservation(String name, LocalDateTime date) {
		this.name = name;
		this.date = date;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}
}
