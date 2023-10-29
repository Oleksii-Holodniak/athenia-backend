package com.athenia.athenia.payload.response;

import org.springframework.http.HttpStatus;

public class MessageResponse {

	private final int status;
	private final String message;

	public MessageResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
