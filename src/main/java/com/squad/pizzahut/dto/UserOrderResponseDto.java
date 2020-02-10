package com.squad.pizzahut.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOrderResponseDto {
	
	private Integer statusCode;
	private String message;
	private List<OrderSummary> orderSummarys;

}
