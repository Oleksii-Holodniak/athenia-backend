package com.athenia.athenia.controller;

import com.athenia.athenia.dto.UserDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.mapper.UserMapper;
import com.athenia.athenia.response.ApiResponse;
import com.athenia.athenia.service.UserService;
import java.util.stream.Stream;
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

	@GetMapping("/info")
	public ApiResponse<UserDTO> getUserInfo() {
		try {
			return new ApiResponse<>(Stream.of(userService.getUser(SecurityContextHolder.getContext().getAuthentication()))
					.map(UserMapper.INSTANCE::userToUserDTO)
					.toList());
		} catch (EntityNotFoundException exception) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}
}
