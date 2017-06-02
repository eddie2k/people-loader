package com.example.peopleloader.filterexpression;

import java.util.function.Predicate;

import com.example.peopleloader.argumentparser.arguments.fields.FieldArgument;
import com.example.peopleloader.argumentparser.arguments.operators.relational.RelationalOperatorArgument;
import com.example.peopleloader.argumentparser.arguments.value.ValueArgument;
import com.example.peopleloader.model.Person;

public class SimpleFilterExpression<T extends Comparable<T>> implements FilterExpression {

	private final FieldArgument<T> fieldNameArg;
	private final RelationalOperatorArgument relationalOperator;
	private final ValueArgument<T> valueArg;

	public SimpleFilterExpression(FieldArgument<T> fieldNameArg, RelationalOperatorArgument relationalOperator,
			ValueArgument<T> valueArg) {
		this.fieldNameArg = fieldNameArg;
		this.relationalOperator = relationalOperator;
		this.valueArg = valueArg;
	}

	@Override
	public Predicate<? super Person> getPredicate() {
		return null;
	}

	@Override
	public String toString() {
		return fieldNameArg + " " + relationalOperator + " " + valueArg;
	}

	public FieldArgument<T> getFieldNameArgument() {
		return fieldNameArg;
	}

	public RelationalOperatorArgument getRelationalOperatorArgument() {
		return relationalOperator;
	}

	public ValueArgument<T> getValueArgument() {
		return valueArg;
	}
}
