package com.example.peopleloader.loader;

import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import com.example.peopleloader.model.Person;

public interface Loader {

	/**
	 * Reads a the given file and stores the content in a {@link Stream}
	 * 
	 * @param filename
	 * 
	 * @throws JsonParseException
	 *             if the contents cannot be parsed
	 * @throws FileNotFoundException
	 *             if the file cannot be read
	 * @throws UncheckedIOException
	 *             if an IO error occurred while reading the file
	 * @return The {@link Stream} with the file contents
	 */
	public abstract Stream<Person> loadFromFile(String filename) throws FileNotFoundException;

}
