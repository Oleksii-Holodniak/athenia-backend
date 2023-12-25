package com.athenia.athenia.dto;

import java.util.List;

public class CourseFullDTO extends CourseDTO {
	private String securityCode;
	private List<UserDTO> owners;
	private List<UserDTO> students;

	public List<UserDTO> getStudents() {
		return students;
	}

	public CourseFullDTO setStudents(List<UserDTO> students) {
		this.students = students;
		return this;
	}

	public List<UserDTO> getOwners() {
		return owners;
	}

	public CourseFullDTO setOwners(List<UserDTO> owners) {
		this.owners = owners;
		return this;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public CourseDTO setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
		return this;
	}
}
