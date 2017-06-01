package com.example.peopleloader.argumentparser;

import java.util.List;

public interface ArgumentParser {

	public abstract ParsedArguments parse(List<String> args);

}
