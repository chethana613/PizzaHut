package com.squad.pizzahut.service;

import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;

public interface UserService {
	public FoodResponseDto getFoodMenu(Long userId) throws UserNotFoundException;

	LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws UserNotFoundException;
	public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId)
			throws UserNotFoundException, FoodNotFoundException;
}
