package com.example.peopleloader.argumentparser.arguments.operators.relational;

public final class EqualToOperator implements RelationalOperatorArgument {

	private static final EqualToOperator INSTANCE = new EqualToOperator();

	private EqualToOperator() {

	}

	public static EqualToOperator getInstance() {
		return INSTANCE;
	}

	@Override
	public <T extends Comparable<T>> boolean apply(T arg1, T arg2) {
		return arg1.compareTo(arg2) == 0;
	}

	@Override
	public String toString() {
		return "==";
	}

}
