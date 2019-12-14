package company18.mctank.forms;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Form containing information about entered reservation.
 * @author CS
 *
 */
public class ReservationForm {
	@NotEmpty
	@Size(min=3)
	private String name;
	
	@NotNull 
	@NotEmpty
	private String mcPoint;
	
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

	public String getMcPoint() {
		return mcPoint;
	}

	public void setMcPoint(String mcPoint) {
		this.mcPoint = mcPoint;
	}

}
