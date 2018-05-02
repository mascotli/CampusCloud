package com.mascot.campuscloud.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error")
public class TokenErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public TokenErrorException(String message) {
		super(message);
	}
}
