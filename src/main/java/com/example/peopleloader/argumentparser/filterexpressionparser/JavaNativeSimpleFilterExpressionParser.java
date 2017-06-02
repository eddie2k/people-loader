package com.example.peopleloader.argumentparser.filterexpressionparser;

import java.time.LocalDate;

import com.example.peopleloader.argumentparser.arguments.fields.BirthDateFieldArgument;
import com.example.peopleloader.argumentparser.arguments.fields.NameFieldArgument;
import com.example.peopleloader.argumentparser.arguments.operators.relational.EqualToOperator;
import com.example.peopleloader.argumentparser.arguments.operators.relational.LessOrEqualToOperator;
import com.example.peopleloader.argumentparser.arguments.operators.relational.RelationalOperatorArgument;
import com.example.peopleloader.argumentparser.arguments.value.BirthDateValueArgument;
import com.example.peopleloader.argumentparser.arguments.value.NameValueArgument;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFieldNameArgumentException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidOperatorArgumentException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidSimpleFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidValueArgumentException;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;
import com.example.peopleloader.filterexpression.constants.BinaryRelationalOperator;
import com.example.peopleloader.filterexpression.constants.FieldName;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;

public class JavaNativeSimpleFilterExpressionParser {

	private static final String SINGLE_WORD_BETWEEN_SINGLE_CURLY_QUOTES_REGEX = "^’[_a-zA-Z0-9]+’$";
	private static final String DATE_BETWEEN_SINGLE_CURLY_QUOTES_REGEX = "^’[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]’$";

	/**
	 * Parses a simple filter. The expected format is:
	 * {@code <fieldName> <op> '<fieldValue>'}
	 * 
	 * @param fieldStr
	 *            the text of the field
	 * @param opStr
	 *            the text of the operator
	 * @param valueStr
	 *            the text of the value surrounded by single quotes
	 * @return the parsed expression
	 * 
	 * @throws InvalidSimpleFilterException
	 *             if the expression cannot be parsed
	 */
	public SimpleFilterExpression<?> parse(String fieldStr, String opStr, String valueStr) {
		if (fieldStr == null) {
			throw new InvalidSimpleFilterException("The field text is null");
		}
		if (opStr == null) {
			throw new InvalidSimpleFilterException("The operator text is null");
		}
		if (valueStr == null) {
			throw new InvalidSimpleFilterException("The value text is null");
		}

		try {
			FieldName fieldName = FieldName.fromString(fieldStr);
			RelationalOperatorArgument operator = getOperator(opStr);

			switch (fieldName) {
			case NAME:
				return new SimpleFilterExpression<Name>(NameFieldArgument.getInstance(), operator,
						getNameValue(valueStr));
			case BIRTHDATE:
				return new SimpleFilterExpression<BirthDate>(BirthDateFieldArgument.getInstance(), operator,
						getBirthDateValue(valueStr));
			default:
				throw new UnsupportedOperationException("Field name " + fieldName + "is known but unsuported");
			}
		} catch (InvalidFieldNameArgumentException | InvalidValueArgumentException
				| InvalidOperatorArgumentException e) {
			throw new InvalidSimpleFilterException(e);
		}
	}

	public static boolean argsNumberForSimpleFilter(int n) {
		return n == 3;
	}

	private static NameValueArgument getNameValue(String valueStr) {
		if (!valueStr.matches(SINGLE_WORD_BETWEEN_SINGLE_CURLY_QUOTES_REGEX)) {
			throw new InvalidValueArgumentException("Name value cannot be parsed: " + valueStr);
		}
		String trimmed = valueStr.substring(1, valueStr.length() - 1);
		return new NameValueArgument(new Name(trimmed));
	}

	private static BirthDateValueArgument getBirthDateValue(String valueStr) {
		if (!valueStr.matches(DATE_BETWEEN_SINGLE_CURLY_QUOTES_REGEX)) {
			throw new InvalidValueArgumentException("BirthDate value cannot be parsed: " + valueStr);
		}
		String trimmed = valueStr.substring(1, valueStr.length() - 1);
		return new BirthDateValueArgument(new BirthDate(LocalDate.parse(trimmed)));
	}

	private static RelationalOperatorArgument getOperator(String opStr) {
		BinaryRelationalOperator operator = BinaryRelationalOperator.fromString(opStr);
		switch (operator) {
		case LET:
			return LessOrEqualToOperator.getInstance();
		case EQ:
			return EqualToOperator.getInstance();
		default:
			throw new UnsupportedOperationException("Operator " + operator + "is known but unsuported");
		}
	}

}
