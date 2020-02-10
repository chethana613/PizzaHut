package com.squad.pizzahut.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserFoodOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userFoodOrderId;
	
	@ManyToOne()
	@JoinColumn(name = "food_id")
	private Food food;
	
	@ManyToOne
	@JoinColumn(name = "userorder_id")
	private UserOrder userOrder;
	
	private Integer quantity;


}
