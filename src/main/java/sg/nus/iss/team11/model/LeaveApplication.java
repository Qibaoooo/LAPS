package sg.nus.iss.team11.model;

import java.time.LocalDate;

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
	private int Id;
	
	@ManyToOne()
	private User user;

	@Column(name = "comment")
	private String comment;

	@Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'WITHDRAWN', 'UPDATED', 'REJECTED')")
	@Enumerated(EnumType.STRING)
	private LeaveApplicationStatusEnum status;
	
	@Column(name = "type", columnDefinition = "ENUM('MedicalLeave', 'AnnualLeave', 'CompensationLeave')")
	@Enumerated(EnumType.STRING)
	private LeaveApplicationTypeEnum type;
	
	@Column(name = "fromdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;

	@Column(name = "todate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate toDate;

	public LeaveApplication(User user, String comment, LeaveApplicationStatusEnum status,
			LeaveApplicationTypeEnum type, LocalDate fromDate, LocalDate toDate) {
		super();
		this.user = user;
		this.comment = comment;
		this.status = status;
		this.type = type;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public LeaveApplication() {
		super();
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public User getUser() {
		return user;
	}

	public void setEmployeeId(User user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LeaveApplicationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(LeaveApplicationStatusEnum status) {
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
	
	
	
}
