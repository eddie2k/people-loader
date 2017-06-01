package com.example.peopleloader;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.peopleloader.argumentparser.ArgumentParser;
import com.example.peopleloader.argumentparser.ParsedArguments;
import com.example.peopleloader.filter.Filter;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.loader.Loader;
import com.example.peopleloader.loader.exception.JsonParseException;
import com.example.peopleloader.model.Person;
import com.example.peopleloader.printer.Printer;

public final class MainDelegate {

	private final ArgumentParser argumentParser;
	private final Loader loader;
	private final Filter filter;
	private final Printer<Person> printer;

	public MainDelegate(ArgumentParser parser, Loader loader, Filter filter, Printer<Person> printer) {
		Objects.requireNonNull(parser);
		Objects.requireNonNull(loader);
		Objects.requireNonNull(filter);
		Objects.requireNonNull(printer);

		this.argumentParser = parser;
		this.loader = loader;
		this.filter = filter;
		this.printer = printer;
	}

	public void run(Object args) {
		Objects.requireNonNull(args);

		try {
			ParsedArguments parsedArguments = argumentParser.parse(args);
			FilterExpression filterExpression = parsedArguments.getFilterExpression();
			String filename = parsedArguments.getFilename();

			Stream<Person> loaded = loader.loadFromFile(filename);
			Stream<Person> filtered = filter.applyFilter(loaded, filterExpression);
			printer.print(filtered);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		} catch (JsonParseException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
		}
		// TODO parse filter exception
	}

}
