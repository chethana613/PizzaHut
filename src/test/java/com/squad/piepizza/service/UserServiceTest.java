package com.squad.piepizza.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.pizzahut.common.PizzaHutEnum;
import com.squad.pizzahut.dto.FoodDetail;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserOrder;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.repository.FoodRepository;
import com.squad.pizzahut.repository.UserFoodOrderRepository;
import com.squad.pizzahut.repository.UserOrderRepository;
import com.squad.pizzahut.repository.UserRepository;
import com.squad.pizzahut.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	FoodRepository foodRepository;

	@Mock
	UserOrderRepository userOrderRepository;

	@Mock
	UserFoodOrderRepository userFoodOrderRepository;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	User user = new User();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	OrderResponseDto orderResponseDto = new OrderResponseDto();
	FoodDetail foodDetail = new FoodDetail();
	Food food = new Food();
	UserOrder userOrder = new UserOrder();
	List<FoodDetail> foodDetails = new ArrayList<>();

	@Before
	public void init() {
		user.setUserId(1L);
		user.setMobile(12345L);
		user.setPassword("sri");
		user.setRole(PizzaHutEnum.Role.USER);
		loginRequestDto.setMobile(12345L);
		loginRequestDto.setPassword("sri");
		loginResponseDto.setUserName("priya");
		loginResponseDto.setRole("user");
		food.setFoodId(1);
		foodDetail.setFoodId(1);
		foodDetails.add(foodDetail);
		orderRequestDto.setFoodList(foodDetails);
		

	}

	@Test
	public void testAuthenticateUserPositive() throws UserNotFoundException {
		Mockito.when(userRepository.findByMobileAndPassword(12345L, "sri")).thenReturn(Optional.of(user));
		LoginResponseDto result = userServiceImpl.authenticateUser(loginRequestDto);
		assertEquals("USER", result.getRole());
	}

	@Test(expected = UserNotFoundException.class)
	public void testAuthenticateUserForUserNotFoundException() throws UserNotFoundException {
		Mockito.when(userRepository.findByMobileAndPassword(2L, "priya")).thenReturn(Optional.of(user));
		userServiceImpl.authenticateUser(loginRequestDto);
	}

	@Test(expected = UserNotFoundException.class)
	public void testPlaceOrderNoEmployee() throws UserNotFoundException, FoodNotFoundException {
		Mockito.when(userRepository.findById(111L)).thenReturn(Optional.of(user));
		userServiceImpl.placeOrder(orderRequestDto, 1L);
	}

	@Test(expected = FoodNotFoundException.class)
	public void convertToFoodOrderItemNegative() throws UserNotFoundException, FoodNotFoundException {
		foodDetail.setFoodId(11);
		Mockito.when(foodRepository.findById(6)).thenReturn(Optional.of(food));
		userServiceImpl.convertToFoodOrderItem(foodDetail, userOrder);
	}

	@Test(expected = FoodNotFoundException.class)
	public void convertToFoodOrderItemVendorFoodNegative() throws UserNotFoundException, FoodNotFoundException {
		foodDetail.setFoodId(6);
		Mockito.when(foodRepository.findById(8)).thenReturn(Optional.of(food));
		userServiceImpl.convertToFoodOrderItem(foodDetail, userOrder);
	}

}
