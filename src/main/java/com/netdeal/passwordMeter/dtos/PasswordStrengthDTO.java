package com.netdeal.passwordMeter.dtos;

import java.io.Serializable;

import com.netdeal.passwordMeter.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordStrengthDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String password;

	private int strength;

	private UserStatus status;

	public PasswordStrengthDTO(String password) {
		super();
		this.password = password;
	}
	
}
