package com.squad.pizzahut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.VendorOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.service.UserOrderService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/vendors")
@Slf4j
public class VendorController {
	
	@Autowired
	UserOrderService userOrderService;
	
	
	/**
	 * 
	 * getOrders method fetch the vendor orders based on the vendorId.
	 * 
	 *  @param Long type vendorId is taken as parameter to get the vendor orders
	 *  
	 *  @return list of vendor orders enclosed with http response;
	 *  
	 *  @throws NotFoundException.class
	 * 
	 */
	@GetMapping("/{vendorId}/orders")
	public ResponseEntity<VendorOrderResponseDto> getOrders(@PathVariable("vendorId") Long vendorId) throws NotFoundException {
		log.info("VendorController getOrders ----> fetching vendor orders");
		if (vendorId == null) {
			log.error("VendorController getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_ID_MISSING);
		}
		VendorOrderResponseDto vendorOrderResponseDto = userOrderService.getVendorOrders(vendorId);
		vendorOrderResponseDto.setMessage(Constant.SUCCESS);
		vendorOrderResponseDto.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.ok().body(vendorOrderResponseDto);
		
	}

}
