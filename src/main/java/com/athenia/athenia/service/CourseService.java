package com.athenia.athenia.service;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.dto.UserDTO;
import com.athenia.athenia.enumeration.CourseReferenceType;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.CourseMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.CourseReference;
import com.athenia.athenia.model.Tag;
import com.athenia.athenia.model.User;
import com.athenia.athenia.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
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
	private UserService userService;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseReferenceService courseReferenceService;
	private static final Logger log = LoggerFactory.getLogger(CourseService.class);

	public List<Course> getAll() {
		return courseRepository.findAll();
	}

	public Course findById(String id) {
		return courseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Course.class, id));
	}

	public Course findBySecurityCode(String securityCode) {
		return courseRepository.findBySecurityCode(securityCode)
				.orElseThrow(() -> new EntityNotFoundException(Course.class, securityCode));
	}

	public Course create(CourseDTO courseDTO, String ownerName) {
		List<Tag> tags = tagService.find(courseDTO.getTags());
		Course course = CourseMapper.INSTANCE.courseDTOToCourse(courseDTO)
				.setTags(tags)
				.setSecurityCode(UUID.randomUUID().toString());
		course = courseRepository.save(course);
		User user = userService.findByUsername(ownerName);
		courseReferenceService.addOwnerReference(course, user);
		return course;
	}

	public Course update(CourseDTO courseDTO) {
		Course course = findById(courseDTO.getId());
		List<Tag> tags = tagService.find(courseDTO.getTags());
		course.setDescription(courseDTO.getDescription())
				.setTitle(courseDTO.getTitle())
				.setTags(tags);
		return courseRepository.save(course);
	}

	public Course addOwner(String courseId, UserDTO userDTO) {
		return addCourseReference(courseId, userDTO, CourseReferenceType.OWNER);
	}

	public Course addStudent(String courseId, UserDTO userDTO) {
		return addCourseReference(courseId, userDTO, CourseReferenceType.STUDENT);
	}

	public Course deleteOwner(String courseId, UserDTO userDTO) {
		return deleteCourseReference(courseId, userDTO, CourseReferenceType.OWNER);
	}

	public Course deleteStudent(String courseId, UserDTO userDTO) {
		return deleteCourseReference(courseId, userDTO, CourseReferenceType.STUDENT);
	}

	public Course addCourseReference(String courseId, UserDTO userDTO, CourseReferenceType courseReferenceType) {
		Course course = findById(courseId);
		User user = userService.findByUsername(userDTO.getUsername());
		List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
		Optional<CourseReference> courseReferenceOptional = courseReferences.stream()
				.filter(findUserReferenceCourse(user, courseReferenceType))
				.findFirst();
		if (courseReferenceOptional.isPresent()) {
			throw new ExistObjectException(CourseReference.class, userDTO.getUsername());
		}
		courseReferenceService.create(course, user, courseReferenceType);
		return findById(courseId);
	}

	public Course deleteCourseReference(String courseId, UserDTO userDTO, CourseReferenceType courseReferenceType) {
		Course course = findById(courseId);
		User user = userService.findByUsername(userDTO.getUsername());
		List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
		Optional<CourseReference> courseReferenceOptional = courseReferences.stream()
				.filter(findUserReferenceCourse(user, courseReferenceType))
				.findFirst();
		if (courseReferenceOptional.isEmpty()) {
			throw new ExistObjectException(CourseReference.class, userDTO.getUsername());
		}
		courseReferenceService.delete(course, user, courseReferenceType);
		return findById(courseId);
	}

	public void delete(String id, String username) {
		Course course = findById(id);
		if (!isOwner(id, username)) {
			throw new RuntimeException("User is not owner, so he can't delete course");
		}
		courseRepository.delete(course);
	}

	public boolean isOwner(String id, String username) {
		try {
			Course course = findById(id);
			User user = userService.findByUsername(username);
			List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
			Optional<CourseReference> courseReferenceOptional = courseReferences.stream()
					.filter(findOwnersCourse(user))
					.findFirst();
			return courseReferenceOptional.isPresent();
		} catch (EntityNotFoundException exception) {
			return false;
		}
	}

	public boolean existInCourseReference(String id, String username) {
		try {
			Course course = findById(id);
			User user = userService.findByUsername(username);
			List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
			Optional<CourseReference> courseReferenceOptional = courseReferences.stream()
					.filter(existInCourse(user))
					.findFirst();
			return courseReferenceOptional.isPresent();
		} catch (EntityNotFoundException exception) {
			return false;
		}
	}

	private Predicate<CourseReference> existInCourse(User user) {
		return courseReference -> courseReference.getUser().equals(user);
	}

	private Predicate<CourseReference> isReference(CourseReferenceType courseReferenceType) {
		return courseReference -> courseReference.getCourseReferenceType() == courseReferenceType;
	}

	private Predicate<CourseReference> findOwnersCourse(User user) {
		return existInCourse(user).and(isReference(CourseReferenceType.OWNER));
	}

	private Predicate<CourseReference> findUserReferenceCourse(User user, CourseReferenceType courseReferenceType) {
		return existInCourse(user).and(isReference(courseReferenceType));
	}
}
