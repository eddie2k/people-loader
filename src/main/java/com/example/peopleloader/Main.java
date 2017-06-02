package com.example.peopleloader;

import java.util.Arrays;

import com.example.peopleloader.argumentparser.ArgumentParser;
import com.example.peopleloader.argumentparser.PlainTextArgumentParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.FilterExpressionParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeCompoundFilterExpressionParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeFilterExpressionParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.JavaNativeSimpleFilterExpressionParser;
import com.example.peopleloader.filter.LiveFilter;
import com.example.peopleloader.loader.JsonFileLoader;
import com.example.peopleloader.loader.Loader;
import com.example.peopleloader.model.Person;
import com.example.peopleloader.printer.ConsolePrinter;
import com.example.peopleloader.printer.Printer;

public class Main {

	private static Printer<Person> printer;
	private static LiveFilter filter;
	private static Loader loader;
	private static ArgumentParser parser;

	public static void main(String[] args) {
		injectDependencies();
		new MainDelegate(parser, loader, filter, printer).run(Arrays.asList(args));
	}

	private static void injectDependencies() {
		JavaNativeSimpleFilterExpressionParser simpleExprParser = new JavaNativeSimpleFilterExpressionParser();
		JavaNativeCompoundFilterExpressionParser compoundExprParser = new JavaNativeCompoundFilterExpressionParser(
				simpleExprParser);
		FilterExpressionParser filterExpressionParser = new JavaNativeFilterExpressionParser(simpleExprParser,
				compoundExprParser);
		parser = new PlainTextArgumentParser(filterExpressionParser);
		loader = new JsonFileLoader();
		filter = new LiveFilter();
		printer = new ConsolePrinter<>();
	}

}
