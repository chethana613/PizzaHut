package com.squad.pizzahut.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponseDto extends ResponseDto{

	private List<FoodResponse> preferenceList;
	private List<FoodMenuResponse> allMenuList;
}
