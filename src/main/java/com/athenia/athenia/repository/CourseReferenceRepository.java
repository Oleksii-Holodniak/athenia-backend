package com.athenia.athenia.repository;

import com.athenia.athenia.enumeration.CourseReferenceType;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.CourseReference;
import com.athenia.athenia.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */
public interface CourseReferenceRepository extends MongoRepository<CourseReference, String> {
	@Query("{ 'courseReferenceType' : ?0, 'course' : ?1, 'user' : ?2 }")
	Optional<CourseReference> findByAllFields(CourseReferenceType courseReferenceType, Course course, User user);

	List<CourseReference> findAllByCourse(Course course);
}
