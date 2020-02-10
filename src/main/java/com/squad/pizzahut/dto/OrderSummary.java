package com.squad.pizzahut.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.squad.pizzahut.common.PizzaHutEnum.OrderStatus;
import com.squad.pizzahut.common.PizzaHutEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummary {
	
	private Long userOrderId;
	private LocalDate orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	private Double totalAmount;
	private List<OrderedFood> orderedFoods;

}
