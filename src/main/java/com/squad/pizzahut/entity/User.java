package com.squad.pizzahut.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.UniqueElements;

import com.squad.pizzahut.common.PizzaHutEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	
	@Id
	private Long userId;
	private String userName;
	@Email
	@UniqueElements
	private String email;
	
	private Long mobile;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String password;
	private String address;
	

}
