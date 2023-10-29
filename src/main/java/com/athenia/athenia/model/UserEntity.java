package com.athenia.athenia.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity {
	@Id
	private String id;
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	@NotBlank
	@Size(max = 20)
	private String username;
	@NotBlank
	@Size(max = 120)
	private String password;

	public UserEntity(String username, String email, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public UserEntity setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getId() {
		return id;
	}

	public UserEntity setId(String id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserEntity setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserEntity setPassword(String password) {
		this.password = password;
		return this;
	}
}
