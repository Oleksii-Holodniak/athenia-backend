package com.athenia.athenia.service;

import com.athenia.athenia.dto.TagDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.model.Tag;
import com.athenia.athenia.repository.TagRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;

	public List<Tag> getAll() {
		return tagRepository.findAll();
	}

	public Tag create(TagDTO tagDTO) {
		Tag existingTag = tagRepository.findByTag(tagDTO.getTag());
		if (existingTag != null) {
			throw new ExistObjectException(Tag.class, tagDTO.getTag());
		}
		Tag newTag = new Tag().setTag(tagDTO.getTag());
		return tagRepository.save(newTag);
	}

	public void delete(String tag) {
		Tag existingTag = tagRepository.findByTag(tag);
		if (existingTag != null) {
			tagRepository.delete(existingTag);
		}
	}

	public List<Tag> find(List<TagDTO> tagsDTO) {
		return tagsDTO.stream()
				.map(tagDTO -> {
					Tag tag = tagRepository.findByTag(tagDTO.getTag());
					if (tag == null) {
						throw new EntityNotFoundException(Tag.class, tagDTO.getTag());
					}
					return tag;
				})
				.toList();
	}

}
