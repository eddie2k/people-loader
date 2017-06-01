package com.example.peopleloader.argumentparser;

import java.util.List;

import com.example.peopleloader.argumentparser.filterexpressionparser.FilterExpressionParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.exception.InvalidProgramArgumentsException;
import com.example.peopleloader.filterexpression.FilterExpression;

public class PlainTextArgumentParser implements ArgumentParser {

	public static final String FILENAME_FLAG = "-f";
	public static final String FILTER_FLAG = "-e";

	private final FilterExpressionParser filterExpressionParser;

	public PlainTextArgumentParser(FilterExpressionParser filterExpressionParser) {
		this.filterExpressionParser = filterExpressionParser;
	}

	@Override
	public ParsedArguments parse(List<String> args) {
		if (args == null) {
			throw new InvalidProgramArgumentsException("Program arguments cannot be null");
		}
		if (args.size() < 2) {
			throw new InvalidProgramArgumentsException("Program arguments are not enough (currenly=" + args + ")");
		}

		try {
			String fileName = parseFileName(args);
			FilterExpression filterExpression = parseFilterExpression(args);
			return new ParsedArguments(fileName, filterExpression);
		} catch (InvalidFilterException e) {
			throw new InvalidProgramArgumentsException(e);
		}
	}

	private static String parseFileName(List<String> args) {
		assert (args != null);

		if (args.get(0).equals(FILENAME_FLAG)) {
			return args.get(1);
		} else {
			throw new InvalidProgramArgumentsException("Filename flag (\"" + FILENAME_FLAG
					+ "\") should the first argument (currently= " + args.get(0) + ")");
		}
	}

	private FilterExpression parseFilterExpression(List<String> args) {
		assert (args != null);
		assert (args.size() >= 2);

		if (args.size() == 2) {
			return filterExpressionParser.parse("");
		} else if (!args.get(2).equals(FILTER_FLAG)) {
			throw new InvalidProgramArgumentsException("Filename flag (\"" + FILTER_FLAG
					+ "\") should the third argument (currently= " + args.get(2) + ")");
		} else {
			if (args.size() == 3) {
				throw new InvalidProgramArgumentsException("Filter expression is empty");
			} else {
				String joinedArguments = String.join(" ", args.subList(3, args.size()));
				return filterExpressionParser.parse(joinedArguments);
			}
		}
	}

}
