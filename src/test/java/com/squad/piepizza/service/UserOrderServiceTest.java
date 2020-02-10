package com.squad.piepizza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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

import com.squad.pizzahut.common.PizzaHutEnum.OrderStatus;
import com.squad.pizzahut.common.PizzaHutEnum.PaymentType;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserFoodOrder;
import com.squad.pizzahut.entity.UserOrder;
import com.squad.pizzahut.entity.VendorFoodCategory;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.repository.UserFoodOrderRepository;
import com.squad.pizzahut.repository.UserOrderRepository;
import com.squad.pizzahut.repository.UserRepository;
import com.squad.pizzahut.repository.VendorFoodCategoryRepository;
import com.squad.pizzahut.service.UserOrderServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserOrderServiceTest {
	
	@InjectMocks
	UserOrderServiceImpl userOrderServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	UserFoodOrderRepository userFoodOrderRepository;
	
	@Mock
	UserOrderRepository userOrderRepository;
	
	@Mock
	VendorFoodCategoryRepository vendorFoodCategoryRepository;
	
	UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto();
	List<UserOrder> userOrders = new ArrayList<>();
	
	List<UserFoodOrder> userFoodOrders = new ArrayList<>();
	UserOrder userOrder = new UserOrder();
	
	List<VendorFoodCategory> vendorFoodCategorys = new ArrayList<>();
	
	VendorFoodCategory vendorFoodCategory = new VendorFoodCategory();
	
	@Before
	public void setup() {
		
		User user = new User();
		user.setAddress("test");
		user.setEmail("tests");
		user.setUserId(1L);
		user.setUserName("test");
		
		userOrder.setOrderDate(LocalDate.now());
		userOrder.setOrderStatus(OrderStatus.ORDERED);
		userOrder.setPaymentType(PaymentType.PAYTM);
		userOrder.setTotalAmount(1.0);
		userOrder.setUserOrderId(1L);
		userOrder.setUser(user);
		userOrders.add(userOrder);
		
		Food food = new Food();
		food.setFoodName("test");
		food.setFoodId(1);
		
		
		
		UserFoodOrder userFoodOrder = new UserFoodOrder();
		userFoodOrder.setFood(food);
		userFoodOrder.setQuantity(2);
		userFoodOrder.setUserOrder(userOrder);
		userFoodOrders.add(userFoodOrder);
		
		vendorFoodCategory.setFood(food);
		
		vendorFoodCategorys.add(vendorFoodCategory);
		
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetUserOrdersException() throws NotFoundException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		userOrderServiceImpl.getUserOrders(2L);
		
	}
	
	@Test
	public void testGetUserOrdersSucess() throws NotFoundException {
		
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		Mockito.when(userFoodOrderRepository.findByUserOrder(userOrder)).thenReturn(userFoodOrders);
		Mockito.when(userOrderRepository.findByUserOrderByUserOrderIdDesc(Mockito.any())).thenReturn(userOrders);
		Long actual = userOrderServiceImpl.getUserOrders(1L).getOrderSummarys().get(0).getUserOrderId();
		
		Long expected = 1L;
		assertEquals(expected, actual);
		
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetVendorOrdersException() throws NotFoundException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		userOrderServiceImpl.getVendorOrders(2L);
		
	}
	
	@Test
	public void testGetVendorOrdersSucess() throws NotFoundException {
		
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		Mockito.when(userFoodOrderRepository.findByFood(Mockito.any())).thenReturn(userFoodOrders);
		Mockito.when(vendorFoodCategoryRepository.findByUser(Mockito.any())).thenReturn(vendorFoodCategorys);
		Long actual = userOrderServiceImpl.getVendorOrders(1L).getVendorOrders().get(0).getUserId();
		Long expected = 1L;
		assertEquals(expected, actual);
		
	}


}
