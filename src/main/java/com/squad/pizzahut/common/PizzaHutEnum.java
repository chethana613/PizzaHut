package com.squad.pizzahut.common;

public class PizzaHutEnum {
	
	public enum Role{
		USER, VENDOR;
	}
	
	public enum OrderStatus{
		PENDING, DELIVERED ,ORDERED;
	}
	
	
	
	public enum PaymentType{
		DEBIT_CARD, UPI,PAYTM,PHONEPE;
	}

}
