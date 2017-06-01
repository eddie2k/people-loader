package com.example.peopleloader.printer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

public class ConsolePrinterTest {

	private static final Person ANY_PERSON = new Person(new Name("Name1"), new BirthDate(LocalDate.of(2017, 05, 31)));
	private static final Person ANOTHER_PERSON = new Person(new Name("Name2"),
			new BirthDate(LocalDate.of(2017, 06, 01)));

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private ConsolePrinter<Person> sut;

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		sut = new ConsolePrinter<Person>();
	}

	@Test
	public void shouldNotPrint_whenStreamIsEmpty() {
		// when
		sut.print(Stream.empty());

		// then
		assertThat(outContent.toString()).isEmpty();
	}

	@Test
	public void shouldPrintAllPerson_whenStreamIsNotEmpty() {
		// when
		sut.print(Stream.of(ANY_PERSON, ANOTHER_PERSON));

		// then
		assertThat(outContent.toString()).isEqualTo(ANY_PERSON.toString() + "\n" + ANOTHER_PERSON.toString() + "\n");
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}
}
