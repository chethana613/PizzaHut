package com.squad.pizzahut.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.squad.pizzahut.common.PizzaHutEnum.OrderStatus;
import com.squad.pizzahut.common.PizzaHutEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "userOrderId", initialValue = 1, allocationSize = 1)
public class UserOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userOrderId")
	private Long userOrderId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	private LocalDate orderDate;
	
	private Double totalAmount;

}
