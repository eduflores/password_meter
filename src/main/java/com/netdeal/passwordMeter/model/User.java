package com.netdeal.passwordMeter.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.netdeal.passwordMeter.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_password")
	private String password;

	private int strength;

	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User parentUser;
	
    @OneToMany(mappedBy = "parentUser")
    private List<User> subUser = new ArrayList<>();
    
    public User(Long id) {
    	this.id = id;    	
    }

	public User(UserDTO dto) {
		this.name = dto.getName();
		this.password = dto.getPassword();
		this.strength = dto.getStrength();
		this.status = dto.getStatus();
		this.parentUser = new User(dto.getParentUser().getId());
	}

}
