package sg.nus.iss.team11.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LeaveApplication {

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

	@Column(name = "type", columnDefinition = "ENUM('MedicalLeave', 'AnnualLeave', 'CompensationLeave')")
	@Enumerated(EnumType.STRING)
	private LeaveApplicationTypeEnum type;

	@Column(name = "fromdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;

	@Column(name = "todate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate toDate;

	public LeaveApplication(LAPSUser user, String description, ApplicationStatusEnum status,
			LeaveApplicationTypeEnum type, LocalDate fromDate, LocalDate toDate) {
		super();
		this.user = user;
		this.description = description;
		this.status = status;
		this.type = type;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public LeaveApplication() {
		super();
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

	public ApplicationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatusEnum status) {
		this.status = status;
	}

	public LeaveApplicationTypeEnum getType() {
		return type;
	}

	public void setType(LeaveApplicationTypeEnum type) {
		this.type = type;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static int countWeekends(final LocalDate start, final LocalDate end) {
	    int weekends = 0;
	    LocalDate date = start;

	    while (!date.isAfter(end)) {
	        DayOfWeek dow = date.getDayOfWeek();
	        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
	            weekends++;
	        }
	        date = date.plusDays(1);
	    }

	    return weekends;
	}
	
	public int countWeekend() {
	    int weekends = 0;
	    LocalDate date = this.getFromDate();

	    while (!date.isAfter(this.getToDate())) {
	        DayOfWeek dow = date.getDayOfWeek();
	        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
	            weekends++;
	        }
	        date = date.plusDays(1);
	    }

	    return weekends;
	}
	
	public int countLeaveDays() {
		int period = (int) ChronoUnit.DAYS.between(this.getFromDate(), this.getToDate());
        return period + 1;
	}

	public boolean isOverlapping(LeaveApplication la) {
	    return fromDate.isBefore(la.getToDate()) && la.getFromDate().isBefore(toDate) || (fromDate.isEqual(la.getFromDate()) || toDate.isEqual(la.getToDate()));
	}
	
	public boolean isOverlapping(LocalDate targetFromDate, LocalDate targetToDate) {
	    return (fromDate.isBefore(targetToDate) && targetFromDate.isBefore(toDate)) || (fromDate.isEqual(targetFromDate) || toDate.isEqual(targetToDate));
	}

	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("username", this.getUser().getUsername());
		json.put("fromDate", this.getFromDate().toString());
		json.put("toDate", this.getToDate().toString());
		json.put("description", this.getDescription() != null ? this.getDescription() : "-");
		json.put("comment", this.getComment() != null ? this.getComment() : "-");
		json.put("status", this.getStatus());
		json.put("type", this.getType().toString());
		return json;
	}



}
