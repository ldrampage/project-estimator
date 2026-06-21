package com.lxbordo.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lxbordo.api.dto.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleAllUnknownExceptions(Exception ex){
    	ex.printStackTrace();
    	log.debug("begin: handleAllUnknownExceptions");
        Response<?> response = Response.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	 @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<Response<?>> handleBadRequestException(BadRequestException ex){
	    	log.debug("begin: handleBadRequestException");
	        Response<?> response = Response.builder()
	                .statusCode(HttpStatus.BAD_REQUEST.value())
	                .message(ex.getMessage())
	                .build();
	        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

}
