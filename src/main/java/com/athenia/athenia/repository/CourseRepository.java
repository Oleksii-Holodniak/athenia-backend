package com.athenia.athenia.repository;

import com.athenia.athenia.model.Course;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
public interface CourseRepository extends MongoRepository<Course, String> {
	Optional<Course> findBySecurityCode(String securityCode);
}
