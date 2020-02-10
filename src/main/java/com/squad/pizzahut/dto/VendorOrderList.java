package com.squad.pizzahut.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorOrderList {
	
	private String userName;
	private String address;
	private Long userId;
	private Double price;
	private String foodName;
	private Integer quantity;
}
