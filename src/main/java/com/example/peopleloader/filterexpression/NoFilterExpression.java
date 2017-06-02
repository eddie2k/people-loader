package com.example.peopleloader.filterexpression;

import java.util.function.Predicate;

import com.example.peopleloader.model.Person;

public final class NoFilterExpression implements FilterExpression {

	private static final NoFilterExpression INSTANCE = new NoFilterExpression();

	private NoFilterExpression() {

	}

	public static NoFilterExpression getInstance() {
		return INSTANCE;
	}

	@Override
	public Predicate<? super Person> getPredicate() {
		return person -> true;
	}

	@Override
	public String toString() {
		return "[NoFilterExpression]";
	}

}
