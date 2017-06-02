package com.example.peopleloader.argumentparser.filterexpressionparser;

import java.util.Arrays;
import java.util.List;

import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.filterexpression.CompoundFilterExpression;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.filterexpression.NoFilterExpression;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;

public class JavaNativeFilterExpressionParser implements FilterExpressionParser {

	public static final String TOKENS_DELIMITER = " ";

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
		return parse(tokens);
	}

	private FilterExpression parse(List<String> tokens) {
		if (argsNumberForSimpleFilter(tokens.size())) {
			return parseSimpleFilterExpr(tokens);
		} else if (argsNumberForCompoundFilter(tokens.size())) {
			return parseCompoundFilter(tokens);
		}else {
			throw new InvalidFilterException("Filter is not valid: " + tokens);
		}
	}

	private static boolean argsNumberForSimpleFilter(int n) {
		return n == 3;
	}

	private SimpleFilterExpression<?> parseSimpleFilterExpr(List<String> tokens) {
		String fieldStr = tokens.get(0);
		String opStr = tokens.get(1);
		String valueStr = tokens.get(2);
		return simpleExprParser.parse(fieldStr, opStr, valueStr);
	}

	private static boolean argsNumberForCompoundFilter(int n) {
		// 3 7 11 15...
		return (n - 3) % 4 == 0;
	}

	private CompoundFilterExpression parseCompoundFilter(List<String> tokens) {
		return compoundExprParser.parse(tokens);
	}

}
