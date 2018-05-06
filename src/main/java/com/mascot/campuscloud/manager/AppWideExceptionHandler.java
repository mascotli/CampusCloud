package com.mascot.campuscloud.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Error> illegalArgument(IllegalArgumentException e) {
		Error error = new Error(e.getMessage());
		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
	}
	
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
    	// System.out.println("error");
        return "errors/404";
    }
    
    @ExceptionHandler(value = Exception.class)
    public String handleInternelServerException(Exception ex) {
    	// System.out.println("error");
        return "errors/404";
    }

}
