package com.squad.pizzahut.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodMenuResponse {

	private String categoryName;
	private List<FoodResponse> foodList;
}
