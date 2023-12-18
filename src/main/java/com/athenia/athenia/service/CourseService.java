package com.athenia.athenia.service;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.mapper.CourseMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Tag;
import com.athenia.athenia.repository.CourseRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
@Service
public class CourseService {

	@Autowired
	private TagService tagService;
	@Autowired
	private CourseRepository courseRepository;
	private static final Logger log = LoggerFactory.getLogger(CourseService.class);

	public List<Course> getAll() {
		return courseRepository.findAll();
	}

	public Course findById(String id) {
		return courseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Course.class, id));
	}

	public Course create(CourseDTO courseDTO) {
		List<Tag> tags = tagService.find(courseDTO.getTags());
		Course course = CourseMapper.INSTANCE.courseDTOToCourse(courseDTO)
				.setTags(tags)
				.setSecurityCode(UUID.randomUUID().toString());
		return courseRepository.save(course);
	}

	public Course update(CourseDTO courseDTO) {
		Course course = findById(courseDTO.getId());
		List<Tag> tags = tagService.find(courseDTO.getTags());
		course.setDescription(courseDTO.getDescription())
				.setTitle(courseDTO.getTitle())
				.setTags(tags);
		return courseRepository.save(course);
	}

	public void delete(String id) {
		try {
			Course course = findById(id);
			courseRepository.delete(course);
		} catch (EntityNotFoundException ex) {
			log.warn("Error to delete:", ex);
		}
	}

}
