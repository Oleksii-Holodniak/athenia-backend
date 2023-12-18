package com.athenia.athenia.controller;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.dto.UserDTO;
import com.athenia.athenia.enumeration.CourseReferenceType;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.CourseMapper;
import com.athenia.athenia.mapper.UserMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.CourseReference;
import com.athenia.athenia.model.User;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.response.MessageResponse;
import com.athenia.athenia.service.CourseReferenceService;
import com.athenia.athenia.service.CourseService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	CourseReferenceService courseReferenceService;

	@GetMapping
	public ListObjectResponse<CourseDTO> getAll() {
		try {
			List<Course> courses = courseService.getAll();
			return convert(courses);
		} catch (EntityNotFoundException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@GetMapping("/{securityCode}")
	public ListObjectResponse<CourseDTO> findBySecurityCode(@PathVariable String securityCode) {
		try {
			Course course = courseService.findBySecurityCode(securityCode);
			return convert(course);
		} catch (EntityNotFoundException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PostMapping
	public ListObjectResponse<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
		try {
			String ownerName = SecurityContextHolder.getContext().getAuthentication().getName();
			return convert((courseService.create(courseDTO, ownerName)));
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PutMapping
	public ListObjectResponse<CourseDTO> update(@RequestBody CourseDTO courseDTO) {
		try {
			return convert((courseService.update(courseDTO)));
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PostMapping("/{courseId}/owner")
	public ListObjectResponse<CourseDTO> addOwner(@PathVariable String courseId,
												  @RequestBody UserDTO userDTO) {
		try {
			return convert(courseService.addOwner(courseId, userDTO));
		} catch (RuntimeException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@PostMapping("/{courseId}/student")
	public ListObjectResponse<CourseDTO> addStudent(@PathVariable String courseId,
													@RequestBody UserDTO userDTO) {
		try {
			return convert(courseService.addStudent(courseId, userDTO));
		} catch (RuntimeException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@DeleteMapping("/{courseId}/owner")
	public ListObjectResponse<CourseDTO> deleteOwner(@PathVariable String courseId,
													 @RequestBody UserDTO userDTO) {
		try {
			return convert(courseService.deleteOwner(courseId, userDTO));
		} catch (RuntimeException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@DeleteMapping("/{courseId}/student")
	public ListObjectResponse<CourseDTO> deleteStudent(@PathVariable String courseId,
													   @RequestBody UserDTO userDTO) {
		try {
			return convert(courseService.deleteStudent(courseId, userDTO));
		} catch (RuntimeException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

	@DeleteMapping("/{id}")
	public MessageResponse<Void> delete(@PathVariable String id) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			courseService.delete(id, username);
			return new MessageResponse<>(HttpStatus.NO_CONTENT);
		} catch (RuntimeException ex) {
			return new MessageResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	private ListObjectResponse<CourseDTO> convert(Course course) {
		return convert(List.of(course));
	}

	private ListObjectResponse<CourseDTO> convert(List<Course> courses) {
		List<CourseDTO> coursesDTO = new ArrayList<>(courses.size());
		for (Course course : courses) {
			List<CourseReference> courseReferences = courseReferenceService.findAllByCourse(course);
			coursesDTO.add(CourseMapper.INSTANCE.courseToCourseDTO(course)
					.setOwners(getUsers(courseReferences, CourseReferenceType.OWNER))
					.setStudents(getUsers(courseReferences, CourseReferenceType.STUDENT)));
		}
		return new ListObjectResponse<>(coursesDTO);
	}

	private List<UserDTO> getUsers(List<CourseReference> courseReferences, CourseReferenceType courseReferenceType) {
		List<User> owners = courseReferences.stream()
				.filter(courseReference -> courseReference.getCourseReferenceType() == courseReferenceType)
				.map(CourseReference::getUser)
				.toList();
		return owners.stream()
				.map(UserMapper.INSTANCE::userToUserDTO)
				.toList();
	}

}
