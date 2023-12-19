package com.athenia.athenia.dto;

import java.util.List;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/19
 */
public class UserInfoDTO extends UserDTO {
	private List<CourseDTO> ownerCourses;
	private List<CourseDTO> studentCourses;

	public List<CourseDTO> getOwnerCourses() {
		return ownerCourses;
	}

	public UserInfoDTO setOwnerCourses(List<CourseDTO> ownerCourses) {
		this.ownerCourses = ownerCourses;
		return this;
	}

	public List<CourseDTO> getStudentCourses() {
		return studentCourses;
	}

	public UserInfoDTO setStudentCourses(List<CourseDTO> studentCourses) {
		this.studentCourses = studentCourses;
		return this;
	}
}
