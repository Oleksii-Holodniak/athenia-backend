package com.athenia.athenia.repository;

import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Lecture;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/27
 */
public interface LectureRepository extends MongoRepository<Lecture, String> {
}
