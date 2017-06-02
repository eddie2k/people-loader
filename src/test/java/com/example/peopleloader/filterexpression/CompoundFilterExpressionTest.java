package com.example.peopleloader.filterexpression;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.Test;

import com.example.peopleloader.argumentparser.arguments.fields.BirthDateFieldArgument;
import com.example.peopleloader.argumentparser.arguments.fields.NameFieldArgument;
import com.example.peopleloader.argumentparser.arguments.operators.logical.LogicalAnd;
import com.example.peopleloader.argumentparser.arguments.operators.relational.EqualToOperator;
import com.example.peopleloader.argumentparser.arguments.operators.relational.LessOrEqualToOperator;
import com.example.peopleloader.argumentparser.arguments.value.BirthDateValueArgument;
import com.example.peopleloader.argumentparser.arguments.value.NameValueArgument;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public class CompoundFilterExpressionTest {
	private static final Name NAME_JOHN = new Name("John");
	private static final BirthDate BIRTH_DATE_2010_01_01 = new BirthDate(LocalDate.of(2010, 1, 1));
	private static final BirthDate BIRTH_DATE_2030_01_01 = new BirthDate(LocalDate.of(2030, 1, 1));

	private SimpleFilterExpression<?> exprNameEqualsJohn = new SimpleFilterExpression<Name>(
			NameFieldArgument.getInstance(), EqualToOperator.getInstance(), new NameValueArgument(NAME_JOHN));
	private SimpleFilterExpression<?> exprBirthDateLET2020_01_01 = new SimpleFilterExpression<BirthDate>(
			BirthDateFieldArgument.getInstance(), LessOrEqualToOperator.getInstance(),
			new BirthDateValueArgument(new BirthDate(LocalDate.of(2020, 01, 01))));

	private CompoundFilterExpression exprNameEqualsJohn_and_BirthDateLET2020_01_01 = new CompoundFilterExpression(
			exprNameEqualsJohn, LogicalAnd.getInstance(), exprBirthDateLET2020_01_01);

	@Test
	public void shouldReturnPredicateThatReturnsTrue_givenPersonHoldsExpression() {
		// when
		Predicate<? super Person> actual = exprNameEqualsJohn_and_BirthDateLET2020_01_01.getPredicate();

		// then
		Person john = new Person(NAME_JOHN, BIRTH_DATE_2010_01_01);
		assertThat(actual.test(john)).isTrue();
	}

	@Test
	public void shouldReturnPredicateThatReturnsTrue_givenPersonDoesntHoldExpression() {
		// when
		Predicate<? super Person> actual = exprNameEqualsJohn_and_BirthDateLET2020_01_01.getPredicate();

		// then
		Person john = new Person(NAME_JOHN, BIRTH_DATE_2030_01_01);
		assertThat(actual.test(john)).isFalse();
	}
}
