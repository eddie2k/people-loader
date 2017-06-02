package com.example.peopleloader.argumentparser.arguments.value;

import com.example.peopleloader.model.Name;

public final class NameValueArgument implements ValueArgument<Name> {

	private final Name value;

	public NameValueArgument(Name value) {
		this.value = value;
	}

	@Override
	public Name getValue() {
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
		if (!(obj instanceof NameValueArgument)) {
			return false;
		}
		NameValueArgument other = (NameValueArgument) obj;
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
