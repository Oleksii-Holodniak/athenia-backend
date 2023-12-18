package com.athenia.athenia.model;

import com.athenia.athenia.enumeration.CourseReferenceType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */

@CompoundIndexes({
		@CompoundIndex(name = "unique_course_user_type", def = "{'course._id': 1, 'user._id': 1, 'courseReferenceType': 1}", unique = true)
})
public class CourseReference {
	@NotNull
	private CourseReferenceType courseReferenceType;
	@NotNull
	@DBRef
	private Course course;
	@NotNull
	@DBRef
	private User user;

	public Course getCourse() {
		return course;
	}

	public CourseReference setCourse(Course course) {
		this.course = course;
		return this;
	}

	public User getUser() {
		return user;
	}

	public CourseReference setUser(User user) {
		this.user = user;
		return this;
	}

	public CourseReferenceType getCourseReferenceType() {
		return courseReferenceType;
	}

	public CourseReference setCourseReferenceType(CourseReferenceType courseReferenceType) {
		this.courseReferenceType = courseReferenceType;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("courseReferenceType=").append(courseReferenceType);
		sb.append(", course=").append(course);
		sb.append(", user=").append(user);
		sb.append('}');
		return sb.toString();
	}
}
