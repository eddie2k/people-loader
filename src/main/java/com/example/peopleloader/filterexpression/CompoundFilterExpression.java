package com.example.peopleloader.filterexpression;

import java.util.function.Predicate;

import com.example.peopleloader.model.Person;

public class CompoundFilterExpression implements FilterExpression {

	@Override
	public Predicate<? super Person> getPredicate() {
		return null;
	}

}
