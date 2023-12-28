package com.athenia.athenia.repository;

import com.athenia.athenia.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/17
 */
public interface CourseRepository extends MongoRepository<Course, String> {
	Optional<Course> findBySecurityCode(String securityCode);

	Optional<Course> findById(String securityCode);

	@Query("{'tags': {'$all': ?0}, 'title': {'$regex': ?1, '$options': 'i'}}")
	Page<Course> findByTagsContainsAndTitleLikeIgnoreCase(List<String> tags, String title, Pageable pageable);
	@Query("{'tags': {'$all': ?0}}")
	Page<Course> findByTagsContainsIgnoreCase(List<String> tags, Pageable pageable);
	Page<Course> findByTitleLikeIgnoreCase(String title, Pageable pageable);
}
