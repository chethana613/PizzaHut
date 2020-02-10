package com.squad.pizzahut.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponse {

	private Integer foodId;
	private String foodName;
	private Double price ;
	private Double rating;
	private String imageUrl;
}
