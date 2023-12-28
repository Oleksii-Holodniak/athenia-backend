package com.athenia.athenia.repository;

import com.athenia.athenia.model.Lecture;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */
public interface LectureRepository extends MongoRepository<Lecture, String> {
}
