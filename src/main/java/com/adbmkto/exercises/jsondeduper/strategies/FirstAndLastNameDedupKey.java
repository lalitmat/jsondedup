package com.adbmkto.exercises.jsondeduper.strategies;

import java.util.Objects;

public class FirstAndLastNameDedupKey {

	private String firstName;
	private String lastName;

	public FirstAndLastNameDedupKey(String firstName, String lastName) {

		this.firstName = firstName;
		this.lastName = lastName;

	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;

		if (!(o instanceof FirstAndLastNameDedupKey))
			return false;

		FirstAndLastNameDedupKey other = (FirstAndLastNameDedupKey) o;

		return Objects.equals(this.getFirstName(), other.getFirstName())
				&& Objects.equals(this.getLastName(), other.getLastName());

	}

	@Override
	public int hashCode() {

		return Objects.hash(this.firstName, this.lastName);

	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
