package com.squad.pizzahut.service;

import com.squad.pizzahut.dto.GetOrderSummaryResponseDto;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.dto.VendorOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;

public interface UserOrderService {
	
	UserOrderResponseDto getUserOrders(Long userId) throws NotFoundException;
	
	VendorOrderResponseDto getVendorOrders(Long userId) throws NotFoundException;
	
	GetOrderSummaryResponseDto getOrderSummary(Long orderId)throws NotFoundException;

}
