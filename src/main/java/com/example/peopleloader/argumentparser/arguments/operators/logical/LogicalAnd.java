package com.example.peopleloader.argumentparser.arguments.operators.logical;

public final class LogicalAnd implements LogicalBinaryOperator {

	private static final LogicalAnd INSTANCE = new LogicalAnd();

	private LogicalAnd() {

	}

	public static LogicalAnd getInstance() {
		return INSTANCE;
	}

	public Boolean apply(Boolean left, Boolean right) {
		return left && right;
	}

	@Override
	public String toString() {
		return "AND";
	}
}
