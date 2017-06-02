package com.example.peopleloader.argumentparser.filterexpressionparser;

import static com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeCompoundFilterExpressionParser.argsNumberForCompoundFilter;
import static com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeSimpleFilterExpressionParser.argsNumberForSimpleFilter;

import java.util.Arrays;
import java.util.List;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.filterexpression.NoFilterExpression;

public class JavaNativeFilterExpressionParser implements FilterExpressionParser {

	private final JavaNativeSimpleFilterExpressionParser simpleExprParser;

	private final JavaNativeCompoundFilterExpressionParser compoundExprParser;

	public JavaNativeFilterExpressionParser(JavaNativeSimpleFilterExpressionParser simpleExprParser,
			JavaNativeCompoundFilterExpressionParser compoundExprParser) {
		this.simpleExprParser = simpleExprParser;
		this.compoundExprParser = compoundExprParser;
	}

	@Override
	public FilterExpression parse(String filterExpression) {
		if (filterExpression == null) {
			throw new InvalidFilterException("Filter expression is null");
		} else if (filterExpression.isEmpty()) {
			return NoFilterExpression.getInstance();
		}

		List<String> tokens = Arrays.asList(filterExpression.split(" "));
		if (argsNumberForSimpleFilter(tokens.size())) {
			String fieldStr = tokens.get(0);
			String opStr = tokens.get(1);
			String valueStr = tokens.get(2);
			return simpleExprParser.parse(fieldStr, opStr, valueStr);
		} else if (argsNumberForCompoundFilter(tokens.size())) {
			return compoundExprParser.parse(tokens);
		} else {
			throw new InvalidFilterException("Filter is not valid: " + tokens);
		}
	}

}
