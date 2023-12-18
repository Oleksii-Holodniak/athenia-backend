package com.athenia.athenia.dto;

import java.util.List;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */
public class CourseDTO {

	private String id;
	private String title;
	private String description;
	private String securityCode;
	private List<TagDTO> tags;
	private List<UserDTO> owners;
	private List<UserDTO> students;

	public List<UserDTO> getStudents() {
		return students;
	}

	public CourseDTO setStudents(List<UserDTO> students) {
		this.students = students;
		return this;
	}

	public List<UserDTO> getOwners() {
		return owners;
	}

	public CourseDTO setOwners(List<UserDTO> owners) {
		this.owners = owners;
		return this;
	}

	public String getId() {
		return id;
	}

	public CourseDTO setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public CourseDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CourseDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public CourseDTO setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
		return this;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public CourseDTO setTags(List<TagDTO> tags) {
		this.tags = tags;
		return this;
	}
}
