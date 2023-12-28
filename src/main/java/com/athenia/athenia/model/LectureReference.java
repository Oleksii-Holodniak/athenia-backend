package com.athenia.athenia.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/27
 */
@CompoundIndexes({
		@CompoundIndex(name = "unique_serial_lecture_course", def = "{'course._id': 1, 'lecture._id': 1}", unique = true)
})
public class LectureReference {
	private Integer serial;
	@NotNull
	@DBRef
	private Course course;
	@NotNull
	@DBRef
	private Lecture lecture;

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
}
