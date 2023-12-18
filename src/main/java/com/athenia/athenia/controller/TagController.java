package com.athenia.athenia.controller;

import com.athenia.athenia.dto.TagDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.TagMapper;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.response.MessageResponse;
import com.athenia.athenia.service.TagService;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */

@RestController
@RequestMapping("/api/tag")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ListObjectResponse<TagDTO> getAll() {
		try {
			return new ListObjectResponse<>(tagService.getAll().stream()
					.map(TagMapper.INSTANCE::tagToTagDTO)
					.toList());
		} catch (EntityNotFoundException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PostMapping
	public ListObjectResponse<TagDTO> create(@RequestBody TagDTO tagDTO) {
		try {
			return new ListObjectResponse<>(Stream.of(tagService.create(tagDTO))
					.map(TagMapper.INSTANCE::tagToTagDTO)
					.toList());
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@DeleteMapping("/{tag}")
	public MessageResponse<Void> delete(@PathVariable String tag) {
		tagService.delete(tag);
		return new MessageResponse<>(HttpStatus.NO_CONTENT);
	}
}
