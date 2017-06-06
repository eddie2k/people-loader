package com.example.peopleloader;

import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.FILENAME_FLAG;
import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.FILTER_FLAG;
import static com.example.peopleloader.argumentparser.PlainTextArgumentParser.TOKENS_DELIMITER;
import static com.example.peopleloader.filterexpression.constants.BinaryRelationalOperator.LET;
import static com.example.peopleloader.filterexpression.constants.FieldName.BIRTHDATE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.join;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.assertj.core.util.Strings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public class MainIT {

	private static final String VALID_JSON_FILE = "src/test/resources/personJsonSamples/valid.json";
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	// String args = +"\"name == 'John_Lennon'\"";

	private static final Person JOHN_LENNON = new Person(new Name("John_Lennon"),
			new BirthDate(LocalDate.parse("1940-10-09")));
	private static final Person PAUL_MCCARTNEY = new Person(new Name("Paul_McCartney"),
			new BirthDate(LocalDate.parse("1942-06-18")));
	private static final Person GEORGE_HARRISON = new Person(new Name("George_Harrison"),
			new BirthDate(LocalDate.parse("1943-02-25")));
	private static final Person RINGO_STAR = new Person(new Name("Ringo_Starr"),
			new BirthDate(LocalDate.parse("1940-07-07")));

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent)); // this seems to be a bit dangerous. Maybe printStream should be an argument to Main?
	}

	@Test
	public void testValidFile_withNoFilter() {
		// when
		String args = Strings.join(FILENAME_FLAG, VALID_JSON_FILE).with(TOKENS_DELIMITER);
		Main.main(args.split(TOKENS_DELIMITER));

		// then
		String expected = join(asList(JOHN_LENNON, PAUL_MCCARTNEY, GEORGE_HARRISON, RINGO_STAR)).with("\n") + "\n";
		assertThat(outContent.toString()).isEqualTo(expected); // this fails on windows due to line separators conflicts: CRLF vs. LF.
		// Consid
	}

	@Test
	public void testValidFile_withFilterForEveryone() {
		// when
		String filterExpression = BIRTHDATE.getText() + TOKENS_DELIMITER + LET.getText() + TOKENS_DELIMITER
				+ "'1900-01-01'";
		String args = Strings.join(FILENAME_FLAG, VALID_JSON_FILE, FILTER_FLAG, filterExpression)
				.with(TOKENS_DELIMITER);
		Main.main(args.split(TOKENS_DELIMITER));

		// then
		String expected = "";
		assertThat(outContent.toString()).isEqualTo(expected);
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

}
