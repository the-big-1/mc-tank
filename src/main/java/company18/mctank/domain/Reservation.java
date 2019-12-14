package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Reservation {
	@Id @GeneratedValue
	private long id;
	
	private String name;
	private String username;
	private LocalDateTime date;
	
	public Reservation() {}
	
	public Reservation(String name, LocalDateTime date, String username) {
		this.name = name;
		this.date = date;
		this.username = username;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}
	
	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public abstract String getMcPoint();
}
