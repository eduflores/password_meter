package com.netdeal.passwordMeter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netdeal.passwordMeter.dtos.UserDTO;
import com.netdeal.passwordMeter.model.User;
import com.netdeal.passwordMeter.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> users = this.service.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody UserDTO dto) {
		User user = this.service.create(dto);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

}
