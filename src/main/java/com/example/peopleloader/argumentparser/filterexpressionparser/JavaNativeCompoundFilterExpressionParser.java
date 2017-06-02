package com.example.peopleloader.argumentparser.filterexpressionparser;

import static com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeSimpleFilterExpressionParser.argsNumberForSimpleFilter;

import java.util.List;
import java.util.Objects;

import com.example.peopleloader.argumentparser.arguments.operators.logical.LogicalAnd;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidCompoundFilterException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidOperatorArgumentException;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidSimpleFilterException;
import com.example.peopleloader.filterexpression.CompoundFilterExpression;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.filterexpression.SimpleFilterExpression;
import com.example.peopleloader.filterexpression.constants.BinaryLogicalOperators;

public class JavaNativeCompoundFilterExpressionParser {

	private final JavaNativeSimpleFilterExpressionParser simpleExprParser;

	public JavaNativeCompoundFilterExpressionParser(JavaNativeSimpleFilterExpressionParser simpleExprParser) {
		Objects.requireNonNull(simpleExprParser);
		this.simpleExprParser = simpleExprParser;
	}

	/**
	 * Parses a compound filter. The expected format is:
	 * {@code <simpleFilter> <op> '<simpleFilter>'}
	 * 
	 * @param tokens
	 *            the {@code List<String> } containing the tokens
	 * @return the parsed expression
	 * 
	 * @throws InvalidCompoundFilterException
	 *             if the expression cannot be parsed
	 */
	public CompoundFilterExpression parse(List<String> tokens) {
		if (tokens == null) {
			throw new InvalidCompoundFilterException("The filter text is null");
		} else if (!argsNumberForCompoundFilter(tokens.size())) {
			throw new InvalidCompoundFilterException("Wrong number of tokens (" + tokens.size() + ")");
		}

		try {
			String leftFieldStr = tokens.get(0);
			String leftOpStr = tokens.get(1);
			String leftValueStr = tokens.get(2);
			String logicalOpStr = tokens.get(3);
			List<String> rightArgs = tokens.subList(4, tokens.size());

			SimpleFilterExpression<?> left = simpleExprParser.parse(leftFieldStr, leftOpStr, leftValueStr);
			BinaryLogicalOperators operator = BinaryLogicalOperators.fromString(logicalOpStr);
			FilterExpression right = buildRight(rightArgs);

			switch (operator) {
			case AND:
				return new CompoundFilterExpression(left, LogicalAnd.getInstance(), right);
			default:
				throw new UnsupportedOperationException("Operator " + operator + "is known but unsuported");
			}
		} catch (InvalidSimpleFilterException | InvalidOperatorArgumentException e) {
			throw new InvalidCompoundFilterException(e);
		}
	}

	private FilterExpression buildRight(List<String> tokens) {
		if (argsNumberForSimpleFilter(tokens.size())) {
			String rightFieldStr = tokens.get(0);
			String rightOpStr = tokens.get(1);
			String rightValueStr = tokens.get(2);
			return simpleExprParser.parse(rightFieldStr, rightOpStr, rightValueStr);
		} else {
			return parse(tokens);
		}
	}

	public static boolean argsNumberForCompoundFilter(int n) {
		// 7 11 15 19...
		return n >= 7 && (n - 7) % 4 == 0;
	}

}
