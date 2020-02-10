package com.squad.pizzahut.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.pizzahut.common.PizzaHutEnum;
import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.designpattern.PaymentRegistry;
import com.squad.pizzahut.dto.FoodDetail;
import com.squad.pizzahut.dto.FoodMenuResponse;
import com.squad.pizzahut.dto.FoodResponse;
import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.entity.Category;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserFoodOrder;
import com.squad.pizzahut.entity.UserOrder;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.repository.CategoryRepository;
import com.squad.pizzahut.repository.FoodRepository;
import com.squad.pizzahut.repository.UserFoodOrderRepository;
import com.squad.pizzahut.repository.UserOrderRepository;
import com.squad.pizzahut.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	UserOrderRepository userOrderRepository;

	@Autowired
	PaymentRegistry paymentRegistry;

	@Autowired
	UserFoodOrderRepository userFoodOrderRepository;

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-07. This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode,role of the user
	 *         and userDetails.
	 * @throws UserNotFoundException it will throw the exception if the user is not
	 *                               registered.
	 * 
	 */
	@Override
	public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws UserNotFoundException {
		Optional<User> user = userRepository.findByMobileAndPassword(loginRequestDto.getMobile(),
				loginRequestDto.getPassword());
		if (!user.isPresent()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		} else {
			LoginResponseDto loginResponseDto = new LoginResponseDto();
			loginResponseDto.setRole(user.get().getRole().toString());
			loginResponseDto.setUserId(user.get().getUserId());
			loginResponseDto.setUserName(user.get().getUserName());
			log.info("Authentication Successful");
			return loginResponseDto;
		}
	}

	/**
	 * This method converts the list obtained from UI to the FoodOrderItem Object
	 * mapping by entities
	 * 
	 * @author PriyaDharshini S
	 * @param foodDetail - contains foodId,quantity
	 * @param UserOrder  - UserOrder table object->contains order details
	 * @return UserFoodOrder - converted persistent object type
	 * @throws FoodNotFoundException - when the ordered food is invalid
	 * 
	 */
	public UserFoodOrder convertToFoodOrderItem(FoodDetail foodDetail, UserOrder userOrder)
			throws FoodNotFoundException {

		Optional<Food> food = foodRepository.findById(foodDetail.getFoodId());
		if (!food.isPresent()) {
			log.error("Exception occured in convertToFoodOrder() method of UserServiceImpl:" + Constant.FOOD_NOT_FOUND);
			throw new FoodNotFoundException(Constant.FOOD_NOT_FOUND);
		}

		UserFoodOrder userFoodOrder = new UserFoodOrder();
		userFoodOrder.setFood(food.get());
		userFoodOrder.setUserOrder(userOrder);
		userFoodOrder.setQuantity(foodDetail.getQuantity());

		return userFoodOrder;
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
	@Transactional()
	public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId)
			throws UserNotFoundException, FoodNotFoundException {
		log.info("Entering into placeOrder() method of EmployeeServiceImpl");
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			log.error("Exception occured in placeOrder() method of EmployeeServiceImpl:" + Constant.USER_NOT_FOUND);
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}

		UserOrder userOrder = new UserOrder();
		BeanUtils.copyProperties(orderRequestDto, userOrder);
		userOrder.setOrderDate(LocalDate.now());
		userOrder.setOrderStatus(PizzaHutEnum.OrderStatus.ORDERED);
		userOrder.setUser(user.get());
		userOrderRepository.save(userOrder);

		// Service locator Factory Bean Changes to perform payment
		String message = paymentRegistry.getServiceBean(orderRequestDto.getPaymentType().toString()).payment();

		// converting the food details and saving it into foodOrderItem table to track
		// the individual food ordered details

		List<FoodDetail> foodDetailList = orderRequestDto.getFoodList();
		List<UserFoodOrder> foodOrderList = foodDetailList.stream().map(index -> {
			try {
				return convertToFoodOrderItem(index, userOrder);
			} catch (FoodNotFoundException e) {
				return null;
			}
		}).collect(Collectors.toList());

		if (Objects.isNull(foodOrderList)) {
			log.error("Exception occured in placeOrder() method of EmployeeServiceImpl:foodOrderList is empty");
			throw new FoodNotFoundException(Constant.EMPTY_CART);
		}

		userFoodOrderRepository.saveAll(foodOrderList);

		OrderResponseDto orderResponseDto = new OrderResponseDto();
		orderResponseDto.setStatusMessage(message);
		orderResponseDto.setFoodOrderId(userOrder.getUserOrderId());
		return orderResponseDto;
	}

	public FoodResponseDto getFoodMenu(Long userId) throws UserNotFoundException {
		log.info("Entering into getFoodMenu() method of UserServiceImpl");
		List<Category> categoryList = categoryRepository.findAll();
		List<Food> foodList = foodRepository.findAll();
		List<FoodMenuResponse> foodMenuResponseList = new ArrayList<>();
		List<FoodResponse> preferenceList = new ArrayList<>();
		if (foodList.isEmpty()) {
			log.debug("Food List is Empty");
			return new FoodResponseDto();
		}
		// logic to get all the menu list
		categoryList.forEach(category -> {
			List<FoodResponse> foodCategoryList = foodList.stream()
					.filter(foodCategory -> foodCategory.getCategory().equals(category))
					.map(foodCategory -> convertFoodToFoodResponse(foodCategory)).collect(Collectors.toList());
			FoodMenuResponse foodMenuResponse = new FoodMenuResponse();
			foodMenuResponse.setCategoryName(category.getCategoryName());
			foodMenuResponse.setFoodList(foodCategoryList);
			foodMenuResponseList.add(foodMenuResponse);
		});

		// logic to get the user preference list
		Optional<User> userResponse = userRepository.findByUserId(userId);
		if (!userResponse.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		List<UserFoodOrder> userFoodOrderList = userFoodOrderRepository.findAll();

		Map<Food, Integer> map = new HashMap<>();
		userFoodOrderList.forEach(index -> {
			if (map.get(index.getFood()) != null) {
				map.put(index.getFood(), map.get(index.getFood()) + 1);
			} else {
				map.put(index.getFood(), 1);
			}
		});

		FoodResponse foodResponse = new FoodResponse();
		
		  Set<Entry<Food, Integer>> foodSet=map.entrySet(); 
		  for(Entry<Food, Integer> index:foodSet) 
		  { BeanUtils.copyProperties(index.getKey(), foodResponse);
		  preferenceList.add(foodResponse); 
		  }
		 
		FoodResponseDto foodResponseDto = new FoodResponseDto();
		foodResponseDto.setAllMenuList(foodMenuResponseList);
		foodResponseDto.setPreferenceList(preferenceList);
		return foodResponseDto;

	}

	private FoodResponse convertFoodToFoodResponse(Food foodCategory) {
		FoodResponse foodResponse = new FoodResponse();
		BeanUtils.copyProperties(foodCategory, foodResponse);
		return foodResponse;
	}

}
