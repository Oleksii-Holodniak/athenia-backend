package com.athenia.athenia.dto;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/05/29
 */
public class UserDTO {
	private String id;
	private String email;
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
