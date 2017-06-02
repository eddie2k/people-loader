package com.example.peopleloader.model;

import java.time.LocalDate;

public final class BirthDate implements Comparable<BirthDate> {

	private final LocalDate date;

	public BirthDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int compareTo(BirthDate other) {
		return date.compareTo(other.date);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		if (!(obj instanceof BirthDate)) {
			return false;
		}
		BirthDate other = (BirthDate) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BirthDate=" + date;
	}

}
