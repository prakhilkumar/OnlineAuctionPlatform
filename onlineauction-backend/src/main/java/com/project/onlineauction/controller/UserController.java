package com.project.onlineauction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.onlineauction.dto.TokenResponse;
import com.project.onlineauction.dto.UserDto;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.service.Userservice;

import Resquest.LoginRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	@Autowired
	Userservice userservice;

	@PostMapping("/addUser")
	@CrossOrigin(origins = "http://localhost:5173/addUser")
	public User addUser(@RequestBody UserDto user){

		return userservice.addUser(user);
	}
	@PostMapping("/loginRequest")
	@CrossOrigin(origins = "http://localhost:5173/loginRequest")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
		TokenResponse tokenResponse = userservice.loginUser(loginRequest);
		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}

	@GetMapping("/user/details")
	public ResponseEntity<String> getUserDetails() {
		return new ResponseEntity<>(userservice.getUserName(),HttpStatus.OK);

	}

}
