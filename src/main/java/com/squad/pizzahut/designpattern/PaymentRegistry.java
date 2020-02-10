package com.squad.pizzahut.designpattern;

import org.springframework.stereotype.Service;

/**
 * This Interface provides the signature of method to get Bean based on the
 * selected Type
 * 
 * @author Chethana
 * @since 05-Feb-2020
 * @version 1.0
 *
 */
@Service
public interface PaymentRegistry {

	public PaymentService getServiceBean(String paymentType);
}
