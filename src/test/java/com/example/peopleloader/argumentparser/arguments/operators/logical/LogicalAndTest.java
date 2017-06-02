package com.example.peopleloader.argumentparser.arguments.operators.logical;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LogicalAndTest {

	LogicalAnd sut = LogicalAnd.getInstance();
	
	@Test
	public void trueAndTrue_returns_true() {
		assertThat(sut.apply(true, true)).isTrue();
	}

	@Test
	public void trueAndFalse_returns_false() {
		assertThat(sut.apply(true, false)).isFalse();
	}

	@Test
	public void falseAndTrue_returns_false() {
		assertThat(sut.apply(false, true)).isFalse();
	}

	@Test
	public void falseAndFalse_returns_false() {
		assertThat(sut.apply(false, false)).isFalse();
	}

}
