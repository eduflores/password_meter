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
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String password;

	private int strength;

	private UserStatus status;
	
	private UserDTO parentUser;

	public UserDTO(Long id) {
		super();
		this.id = id;
	}

	public UserDTO(String name, String password, int strength, UserStatus status, UserDTO parentUser) {
		super();
		this.name = name;
		this.password = password;
		this.strength = strength;
		this.status = status;
		this.parentUser = new UserDTO(parentUser.getId());
	}
	
}
