package com.example.peopleloader.filterexpression.constants;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFieldNameArgumentException;

public enum FieldName {

	NAME("name"), BIRTHDATE("birthDate");

	private final String text;

	private FieldName(String text) {
		this.text = text;
	}

	/**
	 * Parses a field name. E.g.: "name", "birthDate"
	 * 
	 * @param text
	 * @return the parsed field, or throw an exception
	 * 
	 * @throws InvalidFieldNameArgumentException
	 */
	public static FieldName fromString(String text) {
		// FIXME we might use values() static method instead
		switch (text) {
		case "name":
			return NAME;
		case "birthDate":
			return FieldName.BIRTHDATE;
		default:
			throw new InvalidFieldNameArgumentException("Invalid field name: " + text);
		}
	}

	public String getText() {
		return text;
	}

}
