package com.consultorio.core.exceptions;

public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8894194713441067070L;
	
	public ElementNotFoundException() {
		super("No patient was found");
	}

	public ElementNotFoundException(String userId) {
		super("Could not find user '" + userId + "'.");
	}
}
