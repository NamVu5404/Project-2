package com.javaweb.customexception;

public class FieldRequiredException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldRequiredException(String message) {
		super(message);
	}
}
