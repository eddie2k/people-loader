package com.example.peopleloader.filterexpression;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.Test;

import com.example.peopleloader.argumentparser.arguments.fields.NameFieldArgument;
import com.example.peopleloader.argumentparser.arguments.operators.relational.EqualToOperator;
import com.example.peopleloader.argumentparser.arguments.value.NameValueArgument;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public class SimpleFilterExpressionTest {

	private static final BirthDate ANY_BIRTH_DATE = new BirthDate(LocalDate.of(2017, 05, 31));

	private SimpleFilterExpression<?> exprNameEqualsJohn = new SimpleFilterExpression<Name>(
			NameFieldArgument.getInstance(),
			EqualToOperator.getInstance(), new NameValueArgument(new Name("John")));

	@Test
	public void shouldReturnPredicateThatReturnsTrue_givenPersonHoldsExpression() {
		// when
		Predicate<? super Person> actual = exprNameEqualsJohn.getPredicate();

		// then
		Person john = new Person(new Name("John"), ANY_BIRTH_DATE);
		assertThat(actual.test(john)).isTrue();
	}

	@Test
	public void shouldReturnPredicateThatReturnsFalse_givenPersonDoesntHoldExpression() {
		// when
		Predicate<? super Person> actual = exprNameEqualsJohn.getPredicate();

		// then
		Person mary = new Person(new Name("Mary"), ANY_BIRTH_DATE);
		assertThat(actual.test(mary)).isFalse();
	}
}
