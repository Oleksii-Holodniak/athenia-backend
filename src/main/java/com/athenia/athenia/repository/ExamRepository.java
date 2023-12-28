package com.athenia.athenia.repository;

import com.athenia.athenia.model.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/28
 */
public interface ExamRepository extends MongoRepository<Exam, String> {
}
