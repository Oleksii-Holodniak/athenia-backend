package com.athenia.athenia.service;

import com.athenia.athenia.enumeration.CourseReferenceType;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.CourseReference;
import com.athenia.athenia.model.User;
import com.athenia.athenia.repository.CourseReferenceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */
@Service
public class CourseReferenceService {
	@Autowired
	private CourseReferenceRepository courseReferenceRepository;

	public List<CourseReference> findAllByCourse(Course course) {
		return courseReferenceRepository.findAllByCourse(course);
	}

	public void addOwnerReference(Course course, User owner) {
		create(course, owner, CourseReferenceType.OWNER);
	}

	public void addStudentReference(Course course, User student) {
		create(course, student, CourseReferenceType.STUDENT);
	}

	public void create(Course course, User user, CourseReferenceType type) {
		CourseReference courseReference = new CourseReference()
				.setCourseReferenceType(type)
				.setCourse(course)
				.setUser(user);
		if (courseReferenceRepository.findByAllFields(type, course, user).isPresent()) {
			throw new ExistObjectException(CourseReference.class, courseReference.toString());
		}
		courseReferenceRepository.save(courseReference);
	}

	public void delete(Course course, User user, CourseReferenceType type) {
		Optional<CourseReference> courseReference = courseReferenceRepository.findByAllFields(type, course, user);
		courseReference.ifPresent(reference -> courseReferenceRepository.delete(reference));
	}
}
