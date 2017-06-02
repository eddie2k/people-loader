package com.example.peopleloader.argumentparser.filterexpressionparser.exception;

public class InvalidFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFilterException(Exception e) {
		super(e);
	}

	public InvalidFilterException(String msg) {
		super(msg);
	}

}
