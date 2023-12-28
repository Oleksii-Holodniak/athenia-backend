package com.athenia.athenia.repository;

import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Lecture;
import com.athenia.athenia.model.LectureReference;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.LifecycleState;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */
public interface LectureReferenceRepository extends MongoRepository<LectureReference, String> {

	Optional<LectureReference> findByLecture(Lecture lecture);
	List<LectureReference> findByCourse(Course course);
}
