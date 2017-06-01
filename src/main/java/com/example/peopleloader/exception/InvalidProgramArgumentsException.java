package com.example.peopleloader.exception;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;

public class InvalidProgramArgumentsException extends RuntimeException {

	public InvalidProgramArgumentsException(InvalidFilterException e) {
		super(e);
	}

	public InvalidProgramArgumentsException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}
