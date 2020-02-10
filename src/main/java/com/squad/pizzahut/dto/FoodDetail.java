package com.squad.pizzahut.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDetail {

	@NotNull(message="foodId cannot be null")
	private Integer foodId;
	
	@NotNull(message="quantity cannot be null")
	private Integer quantity;
	
	@NotNull(message="price cannot be null")
	private Double price;
}
