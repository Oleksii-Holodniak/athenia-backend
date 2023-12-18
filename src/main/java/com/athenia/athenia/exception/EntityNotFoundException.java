package com.athenia.athenia.exception;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/05/20
 */
public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(Class object, Long id) {
		super("Not fount object " + object.getSimpleName() + " in database by id: " + id);
	}

	public EntityNotFoundException(Class object, String username) {
		super("Not fount object " + object.getSimpleName() + " in database by value: " + username);
	}
}
