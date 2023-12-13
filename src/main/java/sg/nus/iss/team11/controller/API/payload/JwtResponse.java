package sg.nus.iss.team11.controller.API.payload;

import java.util.List;

public class JwtResponse {
	private String jwt;
	private int id;
	private String username;
	private String role;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public JwtResponse(String jwt, int id, String username, String role) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.username = username;
		this.role = role;
	}

}
