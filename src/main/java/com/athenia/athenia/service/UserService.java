package com.athenia.athenia.service;

import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.model.User;
import com.athenia.athenia.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

	public User findByUserName(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new EntityNotFoundException(User.class, username);
		}
		return user.get();
	}

	public User getUser(Authentication authentication) {
		return findByUserName(authentication.getName());
	}
}
