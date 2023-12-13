package sg.nus.iss.team11.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {

	@Id
	private String roleId;

	private String name;

	private String description;

	@OneToMany(mappedBy = "role")
	private List<LAPSUser> users;

	public Role() {
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

	public Role(String roleId, String name, String description) {
		this.roleId = roleId;
		this.name = name;
		this.description = description;
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
