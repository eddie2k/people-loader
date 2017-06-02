package com.example.peopleloader.argumentparser.arguments.operators.relational;

import com.example.peopleloader.argumentparser.arguments.FilterArgument;

public interface RelationalOperatorArgument extends FilterArgument {

	public abstract <T extends Comparable<T>> boolean apply(T arg1, T arg2);

}
