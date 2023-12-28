package com.athenia.athenia.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */

public class LectureReference {
	private Integer serial;
	@NotNull
	@DBRef
	private Course course;
	@DBRef
	private Lecture lecture;

	@DBRef
	private Exam exam;

	public Integer getSerial() {
		return serial;
	}

	public LectureReference setSerial(Integer serial) {
		this.serial = serial;
		return this;
	}

	public Course getCourse() {
		return course;
	}

	public LectureReference setCourse(Course course) {
		this.course = course;
		return this;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public LectureReference setLecture(Lecture lecture) {
		this.lecture = lecture;
		return this;
	}

	public Exam getExam() {
		return exam;
	}

	public LectureReference setExam(Exam exam) {
		this.exam = exam;
		return this;
	}
}
