package sg.nus.iss.team11.model;

import java.time.LocalDate;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CompensationClaim {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne()
	private LAPSUser user;
	
	// comment is for manager to input
	private String comment;

	// description is for staff to input
	private String description;

	@Column(name = "status", columnDefinition = "ENUM('APPLIED', 'UPDATED', 'REJECTED', 'APPROVED', 'CANCELLED', 'DELETED')")
	@Enumerated(EnumType.STRING)
	private ApplicationStatusEnum status;
	
	@Column(name = "compensation_time", columnDefinition = "ENUM('AM', 'PM', 'WHOLEDAY')")
	@Enumerated(EnumType.STRING)
	private CompensationClaimTimeEnum overtimeTime;
	
	@Column(name = "overtime_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate overtimeDate;
	
	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("userid", this.getUser().getUserId());
		json.put("username", this.getUser().getUsername());
		json.put("overtimeDate", this.getOverTimeDate().toString());
		json.put("overtimeTime", this.getOvertimeTime().toString());
		json.put("description", this.getDescription());
		json.put("status", this.getStatus());
		json.put("comment", this.getComment());
		return json;
	}
	
	public CompensationClaim() {}

	public CompensationClaim(LAPSUser user, String description, ApplicationStatusEnum status,
			CompensationClaimTimeEnum overtimeTime, LocalDate overTimeDate) {
		super();
		this.user = user;
		this.description = description;
		this.status = status;
		this.overtimeTime = overtimeTime;
		this.overtimeDate = overTimeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LAPSUser getUser() {
		return user;
	}

	public void setUser(LAPSUser user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApplicationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatusEnum status) {
		this.status = status;
	}

	public CompensationClaimTimeEnum getOvertimeTime() {
		return overtimeTime;
	}

	public void setOvertimeTime(CompensationClaimTimeEnum overtimeTime) {
		this.overtimeTime = overtimeTime;
	}

	public LocalDate getOverTimeDate() {
		return overtimeDate;
	}

	public void setOverTimeDate(LocalDate overTimeDate) {
		this.overtimeDate = overTimeDate;
	}

}
