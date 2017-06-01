package com.example.peopleloader.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

@RunWith(MockitoJUnitRunner.class)
public class LiveFilterTest {

	private static final FilterExpression ANY_EXPRESSION = mock(FilterExpression.class);

	private static final Person ANY_PERSON = new Person(new Name("Name1"), new BirthDate(LocalDate.of(2017, 05, 31)));
	private static final Person ANOTHER_PERSON = new Person(new Name("Name2"),
			new BirthDate(LocalDate.of(2017, 06, 01)));

	private static final Stream<Person> ANY_STREAM = Stream.of(ANY_PERSON, ANOTHER_PERSON);

	private LiveFilter sut;

	@Before
	public void setUp() {
		sut = new LiveFilter();
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAcceptyNullStream() {
		// when
		sut.applyFilter(null, ANY_EXPRESSION);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAcceptyNullExpression() {
		// when
		sut.applyFilter(ANY_STREAM, null);
	}

	@Test
	public void shouldFilterOutAllPerson_whenPredicateIsAlwaysFalse() {
		// given
		FilterExpression alwaysFalseExpr = mock(FilterExpression.class);
		Predicate<Person> alwaysFalse = person -> false;
		willReturn(alwaysFalse).given(alwaysFalseExpr).getPredicate();

		// when
		Stream<Person> filtered = sut.applyFilter(ANY_STREAM, alwaysFalseExpr);

		// then
		assertThat(filtered.count()).isZero();
	}

	@Test
	public void shouldNotFilterPerson_whenPredicateIsAlwaysTrue() {
		// given
		FilterExpression alwaysTrueExpr = mock(FilterExpression.class);
		Predicate<Person> alwaysTrue = person -> true;
		willReturn(alwaysTrue).given(alwaysTrueExpr).getPredicate();

		// when
		Stream<Person> filtered = sut.applyFilter(Stream.of(ANY_PERSON, ANOTHER_PERSON), alwaysTrueExpr);

		// then
		assertThat(filtered.count()).isEqualTo(2);
	}

}
