package company18.mctank.forms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

	@NotEmpty
	@Size(min = 2)
	private String username;
	
	@NotNull 
	@NotEmpty
	private String mcPoint;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern="hh:mm a")
	private LocalTime time;
	
	private int persons;
	
	public ReservationForm(String name, String date, String mcPoint, String username) {
		String[] data = date.split(" ");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		this.name = name;
		this.date = LocalDate.parse(data[0], dateFormatter);
		this.time = LocalTime.parse(data[2] + " " + data[3].toUpperCase(), timeFormatter);
		this.mcPoint = mcPoint;
		this.username = username;
	}
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}
}
