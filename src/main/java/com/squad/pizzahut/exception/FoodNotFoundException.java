package com.squad.pizzahut.exception;

public class FoodNotFoundException extends Exception{

	/**
	 * This Exception is thrown when the food requested is unavailable
	 * @author Chethana M
	 */
	private static final long serialVersionUID = -9206934301716542265L;
	
	public FoodNotFoundException(String message) {
		super(message);
	}

}
