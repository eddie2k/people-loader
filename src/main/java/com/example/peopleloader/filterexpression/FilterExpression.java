package com.example.peopleloader.filterexpression;

import java.util.function.Predicate;

import com.example.peopleloader.model.Person;

public interface FilterExpression {

	public Predicate<? super Person> getPredicate();
}
