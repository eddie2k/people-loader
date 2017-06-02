package com.example.peopleloader.filterexpression.constants;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidOperatorArgumentException;

public enum BinaryRelationalOperator {

	LET("<="), EQ("==");

	private final String text;

	BinaryRelationalOperator(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static BinaryRelationalOperator fromString(String text) {
		switch (text) {
		case "<=":
			return LET;
		case "==":
			return EQ;
		default:
			throw new InvalidOperatorArgumentException("Invalid operator: " + text);
		}
	}

}
