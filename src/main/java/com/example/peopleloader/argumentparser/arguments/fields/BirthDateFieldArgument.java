package com.example.peopleloader.argumentparser.arguments.fields;

import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Person;

public final class BirthDateFieldArgument implements FieldArgument<BirthDate> {

	private static final BirthDateFieldArgument INSTANCE = new BirthDateFieldArgument();

	private BirthDateFieldArgument() {

	}

	public static BirthDateFieldArgument getInstance() {
		return INSTANCE;
	}

	public BirthDate getFieldFrom(Person p) {
		return p.getBirtDate();
	}

	@Override
	public String toString() {
		return "birthDate";
	}
}
