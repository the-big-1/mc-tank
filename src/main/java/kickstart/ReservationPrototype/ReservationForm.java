package kickstart.ReservationPrototype;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationForm {
	@NotEmpty(message="Name must be not empty.")
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime time;
	
	public ReservationForm() {}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
