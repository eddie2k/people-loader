package com.example.peopleloader.argumentparser.filterexpressionparser;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.example.peopleloader.argumentparser.arguments.fields.BirthDateFieldArgument;
import com.example.peopleloader.argumentparser.arguments.fields.NameFieldArgument;
import com.example.peopleloader.argumentparser.arguments.operators.relational.EqualToOperator;
import com.example.peopleloader.argumentparser.arguments.operators.relational.LessOrEqualToOperator;
import com.example.peopleloader.argumentparser.arguments.value.BirthDateValueArgument;
import com.example.peopleloader.argumentparser.arguments.value.NameValueArgument;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidSimpleFilterException;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;
import com.example.peopleloader.filterexpression.constants.BinaryRelationalOperator;
import com.example.peopleloader.filterexpression.constants.FieldName;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;

public class JavaNativeSimpleFilterExpressionParserTest {

	private static final String ANY_WORD = "SINGLEWORD";
	private static final String ANY_FIELD_NAME = FieldName.NAME.getText();
	private static final String ANY_BINARY_REL_OP = BinaryRelationalOperator.LET.getText();
	private static final String ANY_VALUE = "\'" + ANY_WORD + "'";// Note the single quotes
	private static final String ANY_TOKEN = "ANY_TOKEN";
	private static final String ANY_DATE = "2017-05-31";

	private JavaNativeSimpleFilterExpressionParser sut;

	@Before
	public void setUp() {
		sut = new JavaNativeSimpleFilterExpressionParser();
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectNullField() {
		// when
		sut.parse(null, ANY_TOKEN, ANY_TOKEN);
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectNullOp() {
		// when
		sut.parse(ANY_TOKEN, null, ANY_TOKEN);
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectNullValue() {
		// when
		sut.parse(ANY_TOKEN, ANY_TOKEN, null);
	}

	@Test
	public void shouldParseNameField() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(FieldName.NAME.getText(), ANY_BINARY_REL_OP, ANY_VALUE);

		// then
		assertThat(actual.getFieldNameArgument()).isEqualTo(NameFieldArgument.getInstance());
	}

	@Test
	public void shouldParseBirthDateField() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(FieldName.BIRTHDATE.getText(), ANY_BINARY_REL_OP,
				"\'" + ANY_DATE + "\'");

		// then
		assertThat(actual.getFieldNameArgument()).isEqualTo(BirthDateFieldArgument.getInstance());
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectWrongFieldName() {
		// when
		sut.parse("WRONG_FIELD", ANY_BINARY_REL_OP, ANY_VALUE);
	}

	@Test
	public void shouldParseLETOperator() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(ANY_FIELD_NAME, BinaryRelationalOperator.LET.getText(), ANY_VALUE);

		// then
		assertThat(actual.getRelationalOperatorArgument()).isEqualTo(LessOrEqualToOperator.getInstance());
	}

	@Test
	public void shouldParseEqOperator() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(ANY_FIELD_NAME, BinaryRelationalOperator.EQ.getText(), ANY_VALUE);

		// then
		assertThat(actual.getRelationalOperatorArgument()).isEqualTo(EqualToOperator.getInstance());
	}

	@Test
	public void shouldParseNameValue() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(FieldName.NAME.getText(), ANY_BINARY_REL_OP,
				"\'" + ANY_WORD + "'");

		// then
		assertThat(actual.getValueArgument()).isEqualTo(new NameValueArgument(new Name(ANY_WORD)));
	}

	@Test
	public void shouldParseBirthDateValue() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(FieldName.BIRTHDATE.getText(), ANY_BINARY_REL_OP,
				"\'" + ANY_DATE + "\'");

		// then
		assertThat(actual.getValueArgument())
				.isEqualTo(new BirthDateValueArgument(new BirthDate(LocalDate.parse(ANY_DATE))));
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectWrongOperator() {
		// when
		String wrongOp = "WRONGOP";
		sut.parse(ANY_FIELD_NAME, wrongOp, ANY_VALUE);
	}

	@Test
	public void shouldParseFieldInQuoutes() {
		// when
		SimpleFilterExpression<?> actual = sut.parse(ANY_FIELD_NAME, ANY_BINARY_REL_OP, "\'" + ANY_WORD + "\'");

		// then
		assertThat(actual.getValueArgument().getValue()).isEqualTo(new Name(ANY_WORD));
	}

	@Test(expected = InvalidSimpleFilterException.class)
	public void shouldRejectValueWithoutQuoutes() {
		// when
		sut.parse(ANY_FIELD_NAME, ANY_BINARY_REL_OP, ANY_WORD);
	}

}
