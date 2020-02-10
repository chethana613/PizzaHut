package com.squad.pizzahut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.dto.GetOrderSummaryResponseDto;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.service.UserOrderService;
import com.squad.pizzahut.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	UserOrderService userOrderService;

	@Autowired
	UserService userService;

	/**
	 * 
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode,role of the user
	 *         and userDetails.
	 * @throws UserNotFoundException it will throw the exception if the user is not
	 *                               registered.
	 * 
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws UserNotFoundException {
		LoginResponseDto loginResponseDto = userService.authenticateUser(loginRequestDto);
		log.info("Entering into UserController authenticateUser metod: calling UserService");
		loginResponseDto.setStatusMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.SUCCESSFUL_CODE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

	/**
	 * This method is used to place a new order from the existing authorized vendor
	 * stall with their available food menu
	 * 
	 * @author PriyaDharshini S
	 * @param orderRequestDto - Takes parameters like Food Details,Vendor Id,Payment
	 *                        Opted
	 * @param userId          - takes of type Long which is the Employee SAP Id
	 * @return OrderResponseDto - returns places order Id along with the status
	 *         codes
	 * @throws UserNotFoundException - thrown when the logged in employee details is
	 *                               invalid
	 * @throws FoodNotFoundException - thrown when the Food ordered doesn't belong
	 *                               to the existing menu
	 * @since 2020-02-07
	 */
	@PostMapping("/{userId}/orders")
	public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto orderRequestDto,
			@PathVariable Long userId) throws UserNotFoundException, FoodNotFoundException {
		log.info("Entering into placeOrder() method of EmployeeController");
		OrderResponseDto orderResponseDto = userService.placeOrder(orderRequestDto, userId);
		orderResponseDto.setStatusCode(Constant.SUCCESSFUL_CODE);
		return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<UserOrderResponseDto> getOrders(@PathVariable("userId") Long userId)
			throws NotFoundException {
		log.info("UserController getOrders ----> fetching user order");
		if (userId == null) {
			log.error("UserController getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_ID_MISSING);
		}

		UserOrderResponseDto userOrderResponseDto = userOrderService.getUserOrders(userId);
		userOrderResponseDto.setMessage(Constant.SUCCESS);
		userOrderResponseDto.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.ok().body(userOrderResponseDto);
	}

	@GetMapping("/{userId}/orders/{orderId}")
	public ResponseEntity<GetOrderSummaryResponseDto> getOrderSummary(@PathVariable("orderId") Long orderId)
			throws NotFoundException {
		log.info("UserController getOrders ----> fetching user order summary");
		if (orderId == null) {
			log.error("UserController getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_ID_MISSING);
		}

		GetOrderSummaryResponseDto getOrderSummaryResponseDto = userOrderService.getOrderSummary(orderId);
		getOrderSummaryResponseDto.setMessage(Constant.SUCCESS);
		getOrderSummaryResponseDto.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.ok().body(getOrderSummaryResponseDto);
	}

	@GetMapping("/{userId}/foods")
	public ResponseEntity<FoodResponseDto> getFoodMenu(@Valid @PathVariable Long userId) throws UserNotFoundException {
		log.info("Entering into getFoodMenu of UserController");
		FoodResponseDto foodResponseDto = userService.getFoodMenu(userId);
		foodResponseDto.setStatusCode(200);
		foodResponseDto.setStatusMessage("Success");
		return new ResponseEntity<>(foodResponseDto, HttpStatus.OK);
	}

}
