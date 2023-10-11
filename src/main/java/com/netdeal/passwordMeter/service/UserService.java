package com.netdeal.passwordMeter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netdeal.passwordMeter.dtos.PasswordStrengthDTO;
import com.netdeal.passwordMeter.dtos.UserDTO;
import com.netdeal.passwordMeter.model.User;
import com.netdeal.passwordMeter.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordStrengthService passwordStrengthService;

	public List<User> findAll() {
		return this.repository.findAll();
	}

	public User findUserById(Long id) throws Exception {
		return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
	}

	public User findUserByName(String name) throws Exception {
		return this.repository.findUserByName(name).orElseThrow(() -> new Exception("Usuário não encontrado."));
	}

	public User create(UserDTO dto) {
		PasswordStrengthDTO psDTO = this.passwordStrengthService.calculatePasswordStrength(dto.getPassword());
		dto.setStrength(psDTO.getStrength());
		dto.setStatus(psDTO.getStatus());
		User user = new User(dto);
		return this.repository.save(user);
	}

	public User save(User user) {
		return this.repository.save(user);
	}

	public void delete(User user) {
		this.repository.delete(user);
	}

}
