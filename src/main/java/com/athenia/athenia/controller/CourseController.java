package com.athenia.athenia.controller;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.CourseMapper;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.response.MessageResponse;
import com.athenia.athenia.service.CourseService;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {
	@Autowired
	CourseService courseService;

	@GetMapping
	public ListObjectResponse<CourseDTO> getCourse() {
		try {
			return new ListObjectResponse<>(courseService.getAll().stream()
					.map(CourseMapper.INSTANCE::courseToCourseDTO)
					.toList());
		} catch (EntityNotFoundException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PostMapping
	public ListObjectResponse<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
		try {
			return new ListObjectResponse<>(Stream.of(courseService.create(courseDTO))
					.map(CourseMapper.INSTANCE::courseToCourseDTO)
					.toList());
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PutMapping
	public ListObjectResponse<CourseDTO> update(@RequestBody CourseDTO courseDTO) {
		try {
			return new ListObjectResponse<>(Stream.of(courseService.update(courseDTO))
					.map(CourseMapper.INSTANCE::courseToCourseDTO)
					.toList());
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@DeleteMapping("/{id}")
	public MessageResponse<Void> delete(@PathVariable String id) {
		courseService.delete(id);
		return new MessageResponse<>(HttpStatus.NO_CONTENT);
	}

}
