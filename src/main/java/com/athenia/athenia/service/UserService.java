package com.athenia.athenia.service;

import com.athenia.athenia.model.UserEntity;
import com.athenia.athenia.repository.UserRepository;
import jakarta.validation.ValidationException;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/11/12
 */

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;


	public UserEntity findByName(String userName) {
		return userRepository.findByUsername(userName).get();
	}
}
