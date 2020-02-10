package com.squad.piepizza.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squad.pizzahut.controller.UserController;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.service.UserOrderService;
import com.squad.pizzahut.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserOrderService userOrderService;
	
	@Test(expected = NotFoundException.class)
	public void testGetUserOrderUserIdMissing() throws NotFoundException {
		
		userController.getOrders(null);
	}
	
	@Test
	public void testGetUserOrderSuccess() throws NotFoundException {
		Mockito.when(userOrderService.getUserOrders(1L)).thenReturn(new UserOrderResponseDto());
		Integer actual = userController.getOrders(1L).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}

}
