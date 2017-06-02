package com.example.peopleloader.argumentparser.filterexpressionparser;

import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.TOKENS_DELIMITER;
import static com.example.peopleloader.filterexpression.constants.BinaryLogicalOperators.AND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.util.stream.IntStream;

import org.assertj.core.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidCompoundFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidSimpleFilterException;
import com.example.peopleloader.filterexpression.CompoundFilterExpression;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.filterexpression.NoFilterExpression;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JavaNativeFilterExpressionParserTest {

	private static final String ANY_SIMPLE_FILTER_EXPR = "field1" + TOKENS_DELIMITER + "operator1" + TOKENS_DELIMITER
			+ "'value1'";
	private static final String ANOTHER_SIMPLE_FILTER_EXPR = "field2" + TOKENS_DELIMITER + "opreator2"
			+ TOKENS_DELIMITER + "'value2'";
	private static final String ANY_COMPOUND_FILTER_EXPRESSION = ANY_SIMPLE_FILTER_EXPR + TOKENS_DELIMITER + AND
			+ TOKENS_DELIMITER + ANOTHER_SIMPLE_FILTER_EXPR;

	private JavaNativeSimpleFilterExpressionParser simpleExprParser = mock(
			JavaNativeSimpleFilterExpressionParser.class);

	private JavaNativeCompoundFilterExpressionParser compoundExprParser = mock(
			JavaNativeCompoundFilterExpressionParser.class);

	private JavaNativeFilterExpressionParser sut;

	@Before
	public void setUp() {
		sut = new JavaNativeFilterExpressionParser(simpleExprParser, compoundExprParser);
	}

	@Test(expected = InvalidFilterException.class)
	public void shouldRejectNullExpression() {
		// when
		sut.parse(null);
	}

	@Test
	public void shouldReturnNoFilterExpression_whenExpressionIsEmpty() {
		// when
		FilterExpression actual = sut.parse("");

		// then
		assertThat(actual).isEqualTo(NoFilterExpression.getInstance());
	}

	@Test
	public void shoudldParseExpression_givenValidSimpleFilter() {
		// given
		SimpleFilterExpression simpleFilterExpr = mock(SimpleFilterExpression.class);
		willReturn(simpleFilterExpr).given(simpleExprParser).parse(any(), any(), any());

		// when
		FilterExpression actual = sut.parse(ANY_SIMPLE_FILTER_EXPR);

		// then
		assertThat(actual).isEqualTo(simpleFilterExpr);
	}

	@Test(expected = InvalidFilterException.class)
	public void shouldRejectExpression_givenInvalidSimpleFilter() {
		// given
		willThrow(InvalidSimpleFilterException.class).given(simpleExprParser).parse(any(), any(), any());

		// when
		sut.parse(ANY_SIMPLE_FILTER_EXPR);
	}

	@Test
	public void shoudldParseExpression_givenValidCompoundFilter() {
		// given
		CompoundFilterExpression compoundFilterExpr = mock(CompoundFilterExpression.class);
		willReturn(compoundFilterExpr).given(compoundExprParser).parse(any());

		// when
		FilterExpression actual = sut.parse(ANY_COMPOUND_FILTER_EXPRESSION);

		// then
		assertThat(actual).isEqualTo(compoundFilterExpr);
	}

	@Test(expected = InvalidFilterException.class)
	public void shoudldParseExpression_givenInvalidCompoundFilter() {
		// given
		willThrow(InvalidCompoundFilterException.class).given(compoundExprParser).parse(any());

		// when
		sut.parse(ANY_COMPOUND_FILTER_EXPRESSION);
	}

	@SuppressWarnings("unused")
	private Object[] invalidNumberOfTokens() {
		return IntStream.of(1, 2, 4, 6, 8, 9, 10, 12, 13, 14)
				.mapToObj(n -> Strings.join(FilterExpressionUtils.generateNTokens(n)).with(TOKENS_DELIMITER))
				.map(s -> new Object[] { s }).toArray(Object[]::new);
	}

	@Test(expected = InvalidFilterException.class)
	@Parameters(method = "invalidNumberOfTokens")
	public void shouldRejectInvalidNumberOfTokens(String filterExpression) throws FileNotFoundException {
		// when
		sut.parse(filterExpression);
	}
}
