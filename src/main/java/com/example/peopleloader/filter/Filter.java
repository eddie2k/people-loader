package com.example.peopleloader.filter;

import java.util.stream.Stream;

import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.model.Person;

public interface Filter {

	public abstract Stream<Person> applyFilter(Stream<Person> stream, FilterExpression expression);
}
