package com.example.peopleloader;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.UncheckedIOException;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.peopleloader.argumentparser.ArgumentParser;
import com.example.peopleloader.argumentparser.ParsedArguments;
import com.example.peopleloader.exception.InvalidProgramArgumentsException;
import com.example.peopleloader.filter.Filter;
import com.example.peopleloader.filterexpression.FilterExpression;
import com.example.peopleloader.loader.Loader;
import com.example.peopleloader.model.Person;
import com.example.peopleloader.printer.Printer;

@RunWith(MockitoJUnitRunner.class)
public class MainDelegateTest {

	private static final Object ANY_ARGS = new Object();

	private static final String ANY_FILENAME = "ANY_FILENAME";

	private static final Stream<Person> ANY_STREAM = Stream.empty();

	private static final FilterExpression ANY_FILTER_EXPRESSION = null;

	@Mock
	private ArgumentParser argumentParser;

	@Mock
	private Loader loader;

	@Mock
	private Filter filter;

	@Mock
	private Printer<Person> printer;

	private MainDelegate sut;

	@Before
	public void setUp() {
		sut = new MainDelegate(argumentParser, loader, filter, printer);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPE_whenArgsNull() {
		// when
		sut.run(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullParser() {
		// when
		new MainDelegate(null, loader, filter, printer);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullLoader() {
		// when
		new MainDelegate(argumentParser, null, filter, printer);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullFilterer() {
		// when
		new MainDelegate(argumentParser, loader, null, printer);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullPrinter() {
		// when
		new MainDelegate(argumentParser, loader, filter, null);
	}

	@Test
	public void shouldParseArguments() {
		// given
		ParsedArguments parsedArguments = new ParsedArguments(ANY_FILENAME, ANY_FILTER_EXPRESSION);
		given(argumentParser.parse(ANY_ARGS)).willReturn(parsedArguments);

		// when
		sut.run(ANY_ARGS);

		// then
		verify(argumentParser).parse(ANY_ARGS);
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldThrowInvalidProgramArgumentsException_givenParsingFail() {
		// given
		willThrow(InvalidProgramArgumentsException.class).given(argumentParser).parse(any());

		// when
		sut.run(ANY_ARGS);
	}

	@Test
	public void shouldLoadFile_givenParsedFilename() {
		// given
		ParsedArguments parsedArguments = new ParsedArguments(ANY_FILENAME, ANY_FILTER_EXPRESSION);
		given(argumentParser.parse(ANY_ARGS)).willReturn(parsedArguments);

		// when
		sut.run(ANY_ARGS);

		// then
		verify(loader).load(ANY_FILENAME);
	}

	@Test(expected = UncheckedIOException.class)
	public void shouldThrowUncheckedIOException_givenLoadingFail() {
		// given
		ParsedArguments parsedArguments = new ParsedArguments(ANY_FILENAME, ANY_FILTER_EXPRESSION);
		given(argumentParser.parse(ANY_ARGS)).willReturn(parsedArguments);
		willThrow(UncheckedIOException.class).given(loader).load(ANY_FILENAME);

		// when
		sut.run(ANY_ARGS);
	}

	@Test
	public void shouldApplyFilter_givenLoaded() {
		// given
		ParsedArguments parsedArguments = new ParsedArguments(ANY_FILENAME, ANY_FILTER_EXPRESSION);
		given(argumentParser.parse(ANY_ARGS)).willReturn(parsedArguments);
		given(loader.load(ANY_FILENAME)).willReturn(ANY_STREAM);

		// when
		sut.run(ANY_ARGS);

		// then
		verify(filter).applyFilter(ANY_STREAM, ANY_FILTER_EXPRESSION);
	}

	@Test
	public void shouldPrintFilteredPerson_givenLoaded() {
		// given
		ParsedArguments parsedArguments = new ParsedArguments(ANY_FILENAME, ANY_FILTER_EXPRESSION);
		Stream<Person> filteredStream = Stream.empty();
		given(argumentParser.parse(ANY_ARGS)).willReturn(parsedArguments);
		given(loader.load(ANY_FILENAME)).willReturn(ANY_STREAM);
		given(filter.applyFilter(ANY_STREAM, ANY_FILTER_EXPRESSION)).willReturn(filteredStream);

		// when
		sut.run(ANY_ARGS);

		// then
		verify(printer).print(filteredStream);
	}
}
