package com.example.peopleloader.argumentparser.filterexpressionparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.example.peopleloader.argumentparser.arguments.operators.logical.LogicalAnd;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidCompoundFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidSimpleFilterException;
import com.example.peopleloader.filterexpression.CompoundFilterExpression;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;
import com.example.peopleloader.filterexpression.constants.BinaryLogicalOperators;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JavaNativeCompoundFilterExpressionParserTest {

	// Short for "ANY_TOKEN"
	private static final String AT1 = "AT1";
	private static final String AT2 = "AT2";
	private static final String AT3 = "AT3";
	private static final String AT4 = "AT4";
	private static final String AT5 = "AT5";
	private static final String AT6 = "AT6";
	private static final String AT7 = "AT7";
	private static final String AT8 = "AT8";
	private static final String AT9 = "AT9";

	private static final String ANY_LOGICAL_OP = BinaryLogicalOperators.AND.getText();

	private JavaNativeSimpleFilterExpressionParser simpleExprParser = Mockito
			.mock(JavaNativeSimpleFilterExpressionParser.class);

	private JavaNativeCompoundFilterExpressionParser sut;

	@Before
	public void setUp() {
		sut = new JavaNativeCompoundFilterExpressionParser(simpleExprParser);
	}

	@Test(expected = NullPointerException.class)
	public void shouldRejectNullSimpleExprParser() {
		// when
		new JavaNativeCompoundFilterExpressionParser(null);
	}

	@Test(expected = InvalidCompoundFilterException.class)
	public void shouldRejectNullExpression() {
		// when
		sut.parse(null);
	}

	@Test
	public void shouldAccept2SimpleExprs_givenValidLeftAndRight() {
		// given
		SimpleFilterExpression<?> leftExpr = Mockito.mock(SimpleFilterExpression.class);
		SimpleFilterExpression<?> rightExpr = Mockito.mock(SimpleFilterExpression.class);
		willReturn(leftExpr).given(simpleExprParser).parse(AT1, AT2, AT3);
		willReturn(rightExpr).given(simpleExprParser).parse(AT4, AT5, AT6);

		// when
		CompoundFilterExpression actual = sut.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6));

		// then
		assertThat(actual.getLeft()).isEqualTo(leftExpr);
		assertThat(actual.getRight()).isEqualTo(rightExpr);
	}

	@Test(expected = InvalidCompoundFilterException.class)
	public void shouldRejcttSimpleAndSimple_givenValidLeft_invalidRight() {
		// given
		SimpleFilterExpression<?> leftExpr = Mockito.mock(SimpleFilterExpression.class);
		willReturn(leftExpr).given(simpleExprParser).parse(AT1, AT2, AT3);
		willThrow(InvalidSimpleFilterException.class).given(simpleExprParser).parse(AT4, AT5, AT6);

		// when
		sut.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6));
	}

	@Test(expected = InvalidCompoundFilterException.class)
	public void shouldRejcttSimpleAndSimple_givenInvalidLeft_ValidRight() {
		// given
		willThrow(InvalidSimpleFilterException.class).given(simpleExprParser).parse(AT1, AT2, AT3);
		SimpleFilterExpression<?> rightExpr = Mockito.mock(SimpleFilterExpression.class);
		willReturn(rightExpr).given(simpleExprParser).parse(AT4, AT5, AT6);

		// when
		sut.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6));
	}

	@Test(expected = InvalidCompoundFilterException.class)
	public void shouldRejcttSimpleAndSimple_givenInvalidLeft_invalidRight() {
		// given
		willThrow(InvalidSimpleFilterException.class).given(simpleExprParser).parse(AT1, AT2, AT3);
		willThrow(InvalidSimpleFilterException.class).given(simpleExprParser).parse(AT4, AT5, AT6);

		// when
		sut.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6));
	}

	@Test
	public void shouldAccept3SimpleExprs_givenAllAreValid() {
		// given
		SimpleFilterExpression<?> expr1 = Mockito.mock(SimpleFilterExpression.class);
		SimpleFilterExpression<?> expr2 = Mockito.mock(SimpleFilterExpression.class);
		SimpleFilterExpression<?> expr3 = Mockito.mock(SimpleFilterExpression.class);
		willReturn(expr1).given(simpleExprParser).parse(AT1, AT2, AT3);
		willReturn(expr2).given(simpleExprParser).parse(AT4, AT5, AT6);
		willReturn(expr3).given(simpleExprParser).parse(AT7, AT8, AT9);

		// when
		CompoundFilterExpression actual = sut
				.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6, ANY_LOGICAL_OP, AT7, AT8, AT9));

		// then
		assertThat(actual.getLeft()).isEqualTo(expr1);
		assertThat(actual.getRight()).isInstanceOf(CompoundFilterExpression.class);
		assertThat(((CompoundFilterExpression) actual.getRight()).getLeft()).isEqualTo(expr2);
		assertThat(((CompoundFilterExpression) actual.getRight()).getRight()).isEqualTo(expr3);
	}

	@Test
	public void shouldAcceptLogicalAnd() {
		// when
		CompoundFilterExpression actual = sut.parse(Arrays.asList(AT1, AT2, AT3, ANY_LOGICAL_OP, AT4, AT5, AT6));

		// then
		assertThat(actual.getLogicalOperator()).isEqualTo(LogicalAnd.getInstance());
	}

	@Test(expected = InvalidCompoundFilterException.class)
	public void shouldRejectAnyOtherLogicalOp() {
		// when
		String wrongOp = "WRONGOP";
		sut.parse(Arrays.asList(AT1, AT2, AT3, wrongOp, AT4, AT5, AT6));
	}

	@SuppressWarnings("unused")
	private Object[] invalidNumberOfTokens() {
		return IntStream.of(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 13, 14).mapToObj(FilterExpressionUtils::generateNTokens)
				.toArray(Object[]::new);
	}

	@Test(expected = InvalidFilterException.class)
	@Parameters(method = "invalidNumberOfTokens")
	public void shouldRejectInvalidNumberOfTokens(List<String> tokens) {
		// when
		sut.parse(tokens);
	}

}
