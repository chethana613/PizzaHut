package com.squad.piepizza.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.pizzahut.controller.VendorController;
import com.squad.pizzahut.dto.VendorOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.service.UserOrderService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorControllerTest {

	@InjectMocks
	VendorController vendorController;

	@Mock
	UserOrderService userOrderService;

	@Test(expected = NotFoundException.class)
	public void testGetVendorOrderVendorIdMissing() throws NotFoundException {

		vendorController.getOrders(null);
	}

	@Test
	public void testGetVendorOrderSuccess() throws NotFoundException {
		Mockito.when(userOrderService.getVendorOrders(1L)).thenReturn(new VendorOrderResponseDto());
		Integer actual = vendorController.getOrders(1L).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}

}
