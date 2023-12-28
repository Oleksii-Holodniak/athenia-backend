package com.athenia.athenia.dto;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/29
 */
public class ExamCreateDTO extends ExamDTO {
	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public ExamCreateDTO setCourseId(String courseId) {
		this.courseId = courseId;
		return this;
	}
}
