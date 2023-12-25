package com.athenia.athenia.repository;

import com.athenia.athenia.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
public interface CourseRepository extends MongoRepository<Course, String> {
	Optional<Course> findBySecurityCode(String securityCode);

	Optional<Course> findById(String securityCode);

	Page<Course> findByTagsContainsAndTitleLike(List<String> tags, String title, Pageable pageable);
	Page<Course> findByTagsContains(List<String> tags, Pageable pageable);
	Page<Course> findByTitleLike(String title, Pageable pageable);
}
