package com.example.peopleloader.argumentparser.filterexpressionparser;


import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.FILENAME_FLAG;
import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.FILTER_FLAG;
import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.TOKENS_DELIMITER;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.peopleloader.argumentparser.ParsedArguments;
import com.example.peopleloader.argumentparser.PlainTextArgumentParser;
import com.example.peopleloader.argumentparser.filterexpressionparser.exception.InvalidFilterException;
import com.example.peopleloader.exception.InvalidProgramArgumentsException;
import com.example.peopleloader.filterexpression.FilterExpression;

@RunWith(MockitoJUnitRunner.class)
public class PlainTextArgumentParserTest {

	private static final String ANY_STRING = "ANY_STRING";
	private static final String ANOTHER_STRING = "ANOTHER_STRING";
	private static final String A_THIRD_STRING = "A_THIRD_STRING";

	private PlainTextArgumentParser sut;

	@Mock
	private FilterExpressionParser filterExpressionParser;

	@Before
	public void setUp() {
		sut = new PlainTextArgumentParser(filterExpressionParser);
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectNullArguments() {
		// when
		sut.parse(null);
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectLessThan2Arguments() {
		// when
		sut.parse(Arrays.asList(ANY_STRING));
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectArguments_givenFirstArgumentIsNotFilenameFlag() {
		// when
		String wrongFlag = FILENAME_FLAG + "WRONG";
		sut.parse(asList(wrongFlag));

		// then
		verify(filterExpressionParser).parse(Mockito.any());
	}


	@Test
	public void shouldParseFilename_givenFirstArgumentIsFilenameFlag() {
		// when
		ParsedArguments parsed = sut.parse(asList(FILENAME_FLAG, ANY_STRING));

		// then
		assertThat(parsed.getFilename()).isEqualTo(ANY_STRING);
	}
	@Test
	public void shouldParseFilter_givenFilterArgumentsAreNotEmpty_andBetweenQuotes() {
		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, FILTER_FLAG, "“" + ANOTHER_STRING + "”"));

		// then
		verify(filterExpressionParser).parse(Mockito.any());
	}

	@Test
	public void shouldComposeFilterFromThirdAndFollowingArgs() {
		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, FILTER_FLAG, "“" + ANOTHER_STRING, A_THIRD_STRING + "”"));

		// then
		verify(filterExpressionParser).parse(ANOTHER_STRING + TOKENS_DELIMITER + A_THIRD_STRING);
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectArguments_givenWrongFilterFlag() {
		// when
		String wrongFlag = FILTER_FLAG + "WRONG";
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, wrongFlag));
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectArguments_givenFilterFlag_andNoFilterExpression() {
		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, FILTER_FLAG));
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectArguments_givenInvalidFilterExpression() {
		// given
		willThrow(InvalidFilterException.class).given(filterExpressionParser).parse(any());

		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, FILTER_FLAG, ANOTHER_STRING));
	}

	@Test
	public void shouldParseFilterWithEmptyExpression_whenFilterFlagIsMissing() {
		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING));

		// then
		verify(filterExpressionParser).parse("");
	}

	@Test(expected = InvalidProgramArgumentsException.class)
	public void shouldRejectArguments__whenFilterExpressionIsNotBetweenDoubleQuotes() {
		// when
		sut.parse(asList(FILENAME_FLAG, ANY_STRING, FILTER_FLAG, ANOTHER_STRING));
	}

	@Test
	public void shouldReturnParsedFilterExpression() {
		// given
		FilterExpression filterExpression = Mockito.mock(FilterExpression.class);
		BDDMockito.willReturn(filterExpression).given(filterExpressionParser).parse(any());

		// when
		ParsedArguments actual = sut.parse(asList(FILENAME_FLAG, ANY_STRING));

		// then
		assertThat(actual.getFilterExpression()).isEqualTo(filterExpression);
	}

}
