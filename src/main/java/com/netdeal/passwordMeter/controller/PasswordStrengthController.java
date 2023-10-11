package com.netdeal.passwordMeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netdeal.passwordMeter.dtos.PasswordStrengthDTO;
import com.netdeal.passwordMeter.service.PasswordStrengthService;

@RestController
@RequestMapping("/api/pwd-str")
public class PasswordStrengthController {
	
	@Autowired
	private PasswordStrengthService service;
	
	@GetMapping
	@RequestMapping("/{password}")
	public ResponseEntity<PasswordStrengthDTO> getPasswordStrength(@PathVariable("password") String password) {
		PasswordStrengthDTO dto = this.service.calculatePasswordStrength(password);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
