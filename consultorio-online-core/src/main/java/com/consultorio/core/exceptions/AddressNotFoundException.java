package com.consultorio.core.exceptions;

public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8894194713441067070L;
	
	public AddressNotFoundException() {
		super("No patient was found");
	}

	public AddressNotFoundException(String userId) {
		super("Could not find user '" + userId + "'.");
	}
}
