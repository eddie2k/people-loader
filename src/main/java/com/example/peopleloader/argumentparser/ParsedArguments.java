package com.example.peopleloader.argumentparser;

import com.example.peopleloader.filterexpression.FilterExpression;

public final class ParsedArguments {

	private final String filename;
	private final FilterExpression filterExpression;

	public ParsedArguments(String filename, FilterExpression filterExpression) {
		this.filename = filename;
		this.filterExpression = filterExpression;
	}

	public String getFilename() {
		return filename;
	}

	public FilterExpression getFilterExpression() {
		return filterExpression;
	}

}
