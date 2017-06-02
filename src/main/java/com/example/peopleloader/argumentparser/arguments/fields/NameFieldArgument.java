package com.example.peopleloader.argumentparser.arguments.fields;

import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public final class NameFieldArgument implements FieldArgument<Name> {

	private static final NameFieldArgument INSTANCE = new NameFieldArgument();

	private NameFieldArgument() {

	}

	public static NameFieldArgument getInstance() {
		return INSTANCE;
	}

	public Name getFieldFrom(Person p) {
		return p.getName();
	}

	@Override
	public String toString() {
		return "name";
	}
}
