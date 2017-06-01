package com.example.peopleloader.loader;

import java.util.stream.Stream;

import com.example.peopleloader.model.Person;

public interface Loader {

	public abstract Stream<Person> load(String filename);

}
