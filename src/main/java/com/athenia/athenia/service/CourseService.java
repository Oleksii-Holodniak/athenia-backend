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
import io.micrometer.common.util.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/17
 */
@Service
public class CourseService {

	@Autowired
	private TagService tagService;
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseReferenceService courseReferenceService;

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

	public List<Course> search(String title, List<String> tags, Integer limit, Integer page) {
		if (StringUtils.isBlank(title) && tags.get(0).equals("")) {
			return courseRepository.findAll();
		}
		Page<Course> courses = null;
		if (tags.get(0).equals("") && !StringUtils.isBlank(title)) {
			courses = courseRepository.findByTitleLikeIgnoreCase(title, PageRequest.of(page, limit));
		} else if (!tags.get(0).equals("") && StringUtils.isBlank(title)) {
			courses = courseRepository.findByTagsContainsIgnoreCase(tags, PageRequest.of(page, limit));
		} else {
			courses = courseRepository.findByTagsContainsAndTitleLikeIgnoreCase(tags, title, PageRequest.of(page, limit));
		}
		return courses.getContent();
	}

	public Course create(CourseDTO courseDTO, String ownerName, MultipartFile preview) throws IOException {
		List<Tag> tags = tagService.find(courseDTO.getTags());
		String imageUrl = fileService.uploadFile(preview);
		Course course = CourseMapper.INSTANCE.courseDTOToCourse(courseDTO)
				.setTags(tags.stream().map(Tag::getTag).toList())
				.setSecurityCode(UUID.randomUUID().toString())
				.setPreview(imageUrl);
		course = courseRepository.save(course);
		User user = userService.findByUsername(ownerName);
		courseReferenceService.addOwnerReference(course, user);
		return course;
	}

	public Course update(CourseDTO courseDTO, MultipartFile preview) throws IOException {
		Course course = findById(courseDTO.getId());
		String imageUrl = fileService.uploadFile(preview);
		List<Tag> tags = tagService.find(courseDTO.getTags());
		course.setDescription(courseDTO.getDescription())
				.setTitle(courseDTO.getTitle())
				.setTags(tags.stream().map(Tag::getTag).toList())
				.setPreview(imageUrl);
		return courseRepository.save(course);
	}

	public Course addOwner(String courseId, UserDTO userDTO) {
		return addCourseReference(courseId, userDTO, CourseReferenceType.OWNER);
	}

	public Course addStudent(String courseId, UserDTO userDTO) {
		return addCourseReference(courseId, userDTO, CourseReferenceType.STUDENT);
	}

	public Course addStudent(String securityCode, String studentName) {
		Course course = findBySecurityCode(securityCode);
		return addCourseReference(course.getId(), studentName, CourseReferenceType.STUDENT);
	}

	public Course deleteOwner(String courseId, UserDTO userDTO) {
		return deleteCourseReference(courseId, userDTO, CourseReferenceType.OWNER);
	}

	public Course deleteStudent(String courseId, UserDTO userDTO) {
		return deleteCourseReference(courseId, userDTO, CourseReferenceType.STUDENT);
	}

	public Course addCourseReference(String courseId, UserDTO userDTO, CourseReferenceType courseReferenceType) {
		return addCourseReference(courseId, userDTO.getUsername(), courseReferenceType);
	}

	public Course addCourseReference(String courseId, String username, CourseReferenceType courseReferenceType) {
		Course course = findById(courseId);
		User user = userService.findByUsername(username);
		List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
		Optional<CourseReference> courseReferenceOptional = courseReferences.stream()
				.filter(findUserReferenceCourse(user, courseReferenceType))
				.findFirst();
		if (courseReferenceOptional.isPresent()) {
			throw new ExistObjectException(CourseReference.class, username);
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
