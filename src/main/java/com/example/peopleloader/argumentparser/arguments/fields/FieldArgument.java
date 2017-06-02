package com.example.peopleloader.argumentparser.arguments.fields;

import com.example.peopleloader.argumentparser.arguments.FilterArgument;
import com.example.peopleloader.model.Person;

public interface FieldArgument<T> extends FilterArgument {

	public abstract T getFieldFrom(Person p);

}
