package com.example.peopleloader.filterexpression.constants;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidOperatorArgumentException;

public enum BinaryLogicalOperators {

	AND("and");

	private final String text;

	BinaryLogicalOperators(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static BinaryLogicalOperators fromString(String text) {
		switch (text) {
		case "and":
			return AND;
		default:
			throw new InvalidOperatorArgumentException("Invalid operator: " + text);
		}
	}

}
