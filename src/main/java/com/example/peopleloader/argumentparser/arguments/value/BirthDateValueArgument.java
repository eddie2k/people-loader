package com.example.peopleloader.argumentparser.arguments.value;

import com.example.peopleloader.model.BirthDate;

public final class BirthDateValueArgument implements ValueArgument<BirthDate> {

	private final BirthDate value;

	public BirthDateValueArgument(BirthDate value) {
		this.value = value;
	}

	public BirthDate getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BirthDateValueArgument)) {
			return false;
		}
		BirthDateValueArgument other = (BirthDateValueArgument) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return '\'' + value.toString() + '\'';
	}
}
