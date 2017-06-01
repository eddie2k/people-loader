package com.example.peopleloader.filter;

import java.util.Objects;
import java.util.stream.Stream;

import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.model.Person;

/**
 * Filter for a Stream of person in a lazy way.
 */
public class LiveFilter implements Filter {

	@Override
	public Stream<Person> applyFilter(Stream<Person> stream, FilterExpression expression) {
		Objects.requireNonNull(stream);
		Objects.requireNonNull(expression);

		return stream.filter(expression.getPredicate());
	}

}
