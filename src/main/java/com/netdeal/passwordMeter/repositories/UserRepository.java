package com.netdeal.passwordMeter.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netdeal.passwordMeter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findUserById(Long id);

	public Optional<User> findUserByName(String name);

}
