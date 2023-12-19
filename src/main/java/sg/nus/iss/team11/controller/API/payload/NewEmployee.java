package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.EmployeeTypeEnum;

public class NewEmployee {
	private String username;
	private String password;
	private String managerName;
	private String roleName;
	private EmployeeTypeEnum type;
	private int annualLeaveEntitlement;
	private int medicalLeaveEntitlement;
	private int compensationLeaveEntitlement;
	public NewEmployee(String username, String password, String managerName, EmployeeTypeEnum type, String roleName, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, int compensationLeaveEntitlement) {
		super();
		this.username = username;
		this.password = password;
		this.managerName = managerName;
		this.roleName = roleName;
		this.type= type;
		this.annualLeaveEntitlement = annualLeaveEntitlement;
		this.medicalLeaveEntitlement = medicalLeaveEntitlement;
		this.compensationLeaveEntitlement = compensationLeaveEntitlement;
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
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public int getCompensationLeaveEntitlement() {
		return compensationLeaveEntitlement;
	}
	public void setCompensationLeaveEntitlement(int compensationLeaveEntitlement) {
		this.compensationLeaveEntitlement = compensationLeaveEntitlement;
	}
	public EmployeeTypeEnum getType() {
		return type;
	}
	public void setType(EmployeeTypeEnum type) {
		this.type = type;
	}
	
}
