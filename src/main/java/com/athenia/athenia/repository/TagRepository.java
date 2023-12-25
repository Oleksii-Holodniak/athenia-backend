package com.athenia.athenia.repository;

import com.athenia.athenia.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
public interface TagRepository extends MongoRepository<Tag, String> {
	Tag findByTagIgnoreCase(String tag);
}
