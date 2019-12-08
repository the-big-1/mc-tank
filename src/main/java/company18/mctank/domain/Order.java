package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {
	@Id @GeneratedValue
	private long id;
	
	private LocalDateTime date;
	
	public Order(LocalDateTime date) {
		this.date = date;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}
	
	public long getId() {
		return id;
	}

}
