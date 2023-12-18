package sg.nus.iss.team11.controller.API.payload;

import java.util.List;

import sg.nus.iss.team11.model.LAPSUser;

public class NewRole {
	private String roleId;
	private String name;
	private String description;
	private List<LAPSUser> users;
	
	public NewRole(String roldId, String name, String description) {
		super();
		this.roleId=roleId;
		this.name=name;
		this.description=description;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<LAPSUser> getUsers() {
		return users;
	}

	public void setUsers(List<LAPSUser> users) {
		this.users = users;
	}

	

}
