package com.athenia.athenia.controller;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.dto.UserDTO;
import com.athenia.athenia.dto.UserInfoDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.mapper.CourseMapper;
import com.athenia.athenia.mapper.UserMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.CourseReference;
import com.athenia.athenia.model.User;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.service.CourseReferenceService;
import com.athenia.athenia.service.LectureReferenceService;
import com.athenia.athenia.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	LectureReferenceService lectureReferenceService;
	@Autowired
	private CourseReferenceService courseReferenceService;

	@GetMapping("/info")
	public ListObjectResponse<UserDTO> getUserInfo() {
		try {
			User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication());
			List<Course> ownerCourses = courseReferenceService.findAllByUserOwner(user).stream()
					.map(CourseReference::getCourse)
					.toList();
			ownerCourses.forEach(course -> course.setTime(lectureReferenceService.findTime(course)));
			List<Course> studentCourses = courseReferenceService.findAllByUserStudent(user).stream()
					.map(CourseReference::getCourse)
					.toList();
			studentCourses.forEach(course -> course.setTime(lectureReferenceService.findTime(course)));
			List<CourseDTO> ownerCoursesDTO = ownerCourses.stream()
					.map(CourseMapper.INSTANCE::courseToCourseDTO)
					.toList();
			List<CourseDTO> studentCoursesDTO = studentCourses.stream()
					.map(CourseMapper.INSTANCE::courseToCourseDTO)
					.toList();
			UserInfoDTO userInfoDTO = UserMapper.INSTANCE.userToUserInfoDTO(user)
					.setOwnerCourses(ownerCoursesDTO)
					.setStudentCourses(studentCoursesDTO);
			return new ListObjectResponse<>(List.of(userInfoDTO));
		} catch (EntityNotFoundException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}
}
