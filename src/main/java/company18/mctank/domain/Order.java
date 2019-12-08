package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {
	@Id @GeneratedValue
	private long id;
	
	private LocalDateTime orderdate;
	
	public Order(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}
	
	public LocalDateTime getDate() {
		return this.orderdate;
	}
	
	public long getId() {
		return id;
	}

}
