package com.squad.pizzahut.designpattern;

import org.springframework.stereotype.Service;

import com.squad.pizzahut.constant.Constant;

import lombok.extern.slf4j.Slf4j;

/**
 * This Class provides the service of PayTm payment mode
 * 
 * @author Chethana
 * @since 05-Feb-2020
 * @version 1.0
 *
 */
@Service("PAYTM")
@Slf4j


	public class PayTm implements PaymentService {

		@Override
		public String payment() {
			log.info("Executing PayTm:Payment succedded using PAYTM");
			return Constant.PAYTM_MSG;
		}

	
}
