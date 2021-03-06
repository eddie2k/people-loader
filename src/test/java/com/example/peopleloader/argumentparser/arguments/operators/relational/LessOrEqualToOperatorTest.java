package com.example.peopleloader.argumentparser.arguments.operators.relational;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LessOrEqualToOperatorTest {

	LessOrEqualToOperator sut = LessOrEqualToOperator.getInstance();

	@Test
	public void lessThan_returns_true() {
		assertThat(sut.apply(0, 1)).isTrue();
	}

	@Test
	public void equalTo_returns_true() {
		assertThat(sut.apply(0, 0)).isTrue();
	}

	@Test
	public void greaterThan_returns_false() {
		assertThat(sut.apply(1, 0)).isFalse();
	}
}
