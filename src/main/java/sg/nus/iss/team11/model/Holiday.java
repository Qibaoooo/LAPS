package sg.nus.iss.team11.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "holiday_dates")
public class Holiday {
	@Id
	private LocalDate date;
	
	private String description;
	
	public Holiday() {
		super();
	}

	public Holiday(LocalDate date, String description) {
		super();
		this.date = date;
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
