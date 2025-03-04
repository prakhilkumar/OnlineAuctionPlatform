package com.project.onlineauction.exceptions;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomAutionExceptionHandler {

	@ExceptionHandler({AuctionException.class})
    public ResponseEntity<Map<String, String>> handleAuctionException(final AuctionException ex) {	
		Map<String, String> errorMsg = new HashMap<>();
		errorMsg.put("msg", ex.getMessage());
		errorMsg.put("code", ex.getCode());
		ex.printStackTrace();
		return new ResponseEntity<Map<String, String>>(errorMsg,HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler({Exception.class})
    public ResponseEntity<Map<String, String>> handleAll(final RuntimeException ex) {	
		Map<String, String> errorMsg = new HashMap<>();
		errorMsg.put("msg", ex.getMessage());
		errorMsg.put("code", "500");
		ex.printStackTrace();
		return new ResponseEntity<Map<String, String>>(errorMsg,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
