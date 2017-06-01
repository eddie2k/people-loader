package com.example.peopleloader.loader;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.peopleloader.loader.exception.JsonParseException;
import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JsonFileLoaderTest {

	private JsonFileLoader sut;

	@Before
	public void setUp() {
		sut = new JsonFileLoader();
	}

	@Test(expected = NullPointerException.class)
	public void shouldRejectNullFilename() throws FileNotFoundException {
		// when
		sut.loadFromFile(null);
	}

	@Test(expected = FileNotFoundException.class)
	public void shouldRejectUnexistingFileFilename() throws FileNotFoundException {
		// when
		sut.loadFromFile("unexisting_file.json");
	}

	@Test
	public void shouldAcceptTestfile() throws FileNotFoundException {
		// when
		Stream<Person> loaded = sut.loadFromFile("src/test/resources/personJsonSamples/valid.json");

		List<Person> expected = Arrays.asList(
				new Person(new Name("John_Lennon"), new BirthDate(LocalDate.parse("1940-10-09"))),
				new Person(new Name("Paul_McCartney"), new BirthDate(LocalDate.parse("1942-06-18"))),
				new Person(new Name("George_Harrison"), new BirthDate(LocalDate.parse("1943-02-25"))),
				new Person(new Name("Ringo_Starr"), new BirthDate(LocalDate.parse("1940-07-07"))));
		// then
		assertThat(loaded.collect(toList())).containsExactlyElementsOf(expected);
	}

	@Test(expected = JsonParseException.class)
	@Parameters(method = "invalidFiles")
	public void shouldThrowJsonParseException_givenInvalidContent(String filename) throws FileNotFoundException {
		// when
		sut.loadFromFile(filename);
	}

	@SuppressWarnings("unused")
	private Object[] invalidFiles() {
		return new Object[] {
				new Object[] { "src/test/resources/personJsonSamples/invalid_no_name.json" },
				new Object[] { "src/test/resources/personJsonSamples/invalid_no_birthday.json" },
				new Object[] { "src/test/resources/personJsonSamples/invalid_malformed_date.json" } };
	}
}
