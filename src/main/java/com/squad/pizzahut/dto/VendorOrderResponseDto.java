package com.squad.pizzahut.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorOrderResponseDto {
	
	private Integer statusCode;
	private String message;
	private List<VendorOrderList> vendorOrders;


}
