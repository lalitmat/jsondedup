package com.adbmkto.exercises.jsondeduper.strategies;

import java.util.Objects;

/**
 * A composite key implementation for the firstName and lastName based
 * de-Duping.
 * 
 * Added here as an example to indicate the options for future extension as the
 * lead schema changes and additional interesting attributes are added to the
 * schema that may be useful as de-duping strategies.
 * 
 * @author lalit
 *
 */
public class FirstAndLastNameDedupKey {

	private String firstName;
	private String lastName;

	public FirstAndLastNameDedupKey(String firstName, String lastName) {

		this.firstName = firstName;
		this.lastName = lastName;

	}

	/**
	 * Default equals override - in this case, equals and hashcode overrides are
	 * needed since in this strategy we wont be using a class like String for the
	 * de-dup key which has deep Equals and hashcode implementations already
	 * available as part of the API.
	 * 
	 * Checking if a Lead already exists in the dedupTracker Map of the
	 * FirstAndLastNameBasedDedupStrategy will require an override of both equals
	 * and hashcode.
	 */
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

	/**
	 * If equals is overriden, hashcode also needs to be overridden and the contract
	 * between equals and hashcode needs to be maintained. If two objects are equal,
	 * their hashcodes must also be equal though two unequal objects can have the
	 * same hashcodes (causing collisions in map lookups).
	 */
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
