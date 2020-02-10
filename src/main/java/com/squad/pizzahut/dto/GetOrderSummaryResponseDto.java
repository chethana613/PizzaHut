package com.squad.pizzahut.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderSummaryResponseDto {
	
	private Long orderId;
	private Double amount;
	private List<OrderedFood> orderedfoods;
	private Integer statusCode;
	private String message;

}
