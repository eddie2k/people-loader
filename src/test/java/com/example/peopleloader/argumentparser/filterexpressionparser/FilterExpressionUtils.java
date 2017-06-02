package com.example.peopleloader.argumentparser.filterexpressionparser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FilterExpressionUtils {
	private static final String ANY_TOKEN = "ANY_TOKEN";

	private FilterExpressionUtils() {

	}

	public static List<String> generateNTokens(int n) {
		return Stream.<String>generate(() -> ANY_TOKEN).limit(n).collect(Collectors.toList());
	}
}
