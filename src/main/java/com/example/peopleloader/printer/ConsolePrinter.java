package com.example.peopleloader.printer;

import java.util.stream.Stream;

public class ConsolePrinter<T> implements Printer<T> {

	public ConsolePrinter() {

	}

	@Override
	public void print(Stream<T> stream) {
		stream.forEach(System.out::println);
	}

}
