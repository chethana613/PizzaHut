package com.squad.pizzahut.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto extends ResponseDto{
	
	private String role;
	private Long userId;
	private String userName;

}
