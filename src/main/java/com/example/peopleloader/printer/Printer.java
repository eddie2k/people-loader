package com.example.peopleloader.printer;

import java.util.stream.Stream;

public interface Printer<T> {

	public abstract void print(Stream<T> filteredStream);
}
