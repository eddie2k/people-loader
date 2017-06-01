package com.example.peopleloader.argumentparser.filterexpressionparser;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.filterexpression.FilterExpression;

public interface FilterExpressionParser {

	/**
	 * Parses the given expression to create a {@link FilterExpression}
	 * 
	 * @param filterExpression
	 *            the expression to be parsed
	 * @return the parsed expression
	 * 
	 * @throws InvalidFilterException
	 *             if the expression cannot be parsed
	 */
	public FilterExpression parse(String filterExpression);

}
