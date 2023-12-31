package sg.nus.iss.team11.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "laps_users")
public class LAPSUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	private int managerId;
	@NotBlank(message = "Username is required")
	private String username;
	@NotBlank(message = "Password is required")
	private String password;

	private int annualLeaveEntitlement;
	private int medicalLeaveEntitlement;
	private double compensationLeaveEntitlement;
	
	@Column(name = "type", columnDefinition = "ENUM('Administrative', 'Professional')")
	@Enumerated(EnumType.STRING)
	private EmployeeTypeEnum type;

	@ManyToOne
	private Role role;

	@OneToMany(mappedBy = "user")
	private List<LeaveApplication> leaveApplications;

	@OneToMany(mappedBy = "user")
	private List<CompensationClaim> compensationClaim;
	
	public LAPSUser(String username, String password, int annualLeaveEntitlement, int medicalLeaveEntitlement,
			int compensationLeaveEntitlement, Role role, ArrayList<LeaveApplication> leaveApplications) {
		super();
		this.username = username;
		this.password = password;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.compensationLeaveEntitlement = compensationLeaveEntitlement;
		this.role = role;
		this.leaveApplications = leaveApplications;
	}

	public LAPSUser(String username, String password, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public LAPSUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LAPSUser(@NotBlank(message = "Username is required") String username,
			@NotBlank(message = "Password is required") String password, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, double compensationLeaveEntitlement, EmployeeTypeEnum type, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.compensationLeaveEntitlement = compensationLeaveEntitlement;
		this.type = type;
		this.role = role;
	}

	public LAPSUser() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<LeaveApplication> getLeaveApplications() {
		return leaveApplications;
	}

	public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
		this.leaveApplications = leaveApplications;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAnnualLeaveEntitlement() {
		return annualLeaveEntitlement;
	}

	public void setAnnualLeaveEntitlement(int annualLeaveEntitlement) {
		this.annualLeaveEntitlement = annualLeaveEntitlement;
	}

	public int getMedicalLeaveEntitlement() {
		return medicalLeaveEntitlement;
	}

	public void setMedicalLeaveEntitlement(int medicalLeaveEntitlement) {
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
	}

	public double getCompensationLeaveEntitlement() {
		return compensationLeaveEntitlement;
	}

	public void setCompensationLeaveEntitlement(double compensationLeaveEntitlement) {
		this.compensationLeaveEntitlement = compensationLeaveEntitlement;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public LAPSUser setManager(LAPSUser manager) {
		this.managerId = manager.getUserId();
		return this;
	}

	public List<CompensationClaim> getCompensationClaim() {
		return compensationClaim;
	}

	public void setCompensationClaim(List<CompensationClaim> compensationClaim) {
		this.compensationClaim = compensationClaim;
	}

	public EmployeeTypeEnum getType() {
		return type;
	}

	public void setType(EmployeeTypeEnum type) {
		this.type = type;
	}

}
