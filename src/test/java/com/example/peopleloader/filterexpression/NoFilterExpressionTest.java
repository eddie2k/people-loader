package com.example.peopleloader.filterexpression;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.Test;

import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public class NoFilterExpressionTest {

	private NoFilterExpression sut = NoFilterExpression.getInstance();
	private static final BirthDate ANY_BIRTH_DATE = new BirthDate(LocalDate.of(2017, 05, 31));

	@Test
	public void shouldReturnPredicateThatReturnsAlwaysTrue() {
		// when
		Predicate<? super Person> actual = sut.getPredicate();

		// then
		Person john = new Person(new Name("John"), ANY_BIRTH_DATE);
		assertThat(actual.test(null)).isTrue();
		assertThat(actual.test(john)).isTrue();
	}
}
