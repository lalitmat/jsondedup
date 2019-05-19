package com.adbmkto.exercises.jsondeduper.models;

import java.util.Date;
import java.util.Objects;

/**
 * A simple POJO based model for the Lead object. Used by frameworks like
 * Jackson (or GSON or others) as a target for the deserialization from a file
 * containig JSON Lead representations.
 * 
 * No Jackson annotations added - preferring Jackson Defaults.
 * 
 * @author lalit
 *
 */
public class Lead {

	private String _id;

	private String email;

	private String firstName;

	private String lastName;

	private String address;

	private Date entryDate;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * NOTE: The equals override here is NOT being used for the DeDup process All
	 * available instance variables are being checked here to implement a deepEquals
	 * mainly for List membership checks during unit tests. Implementing the Dedup
	 * constraints as listed (either _id OR email can be same) leveraging the equals
	 * and hashcode would be problematic since the implementing an equals with those
	 * constraints would lead to a non transitive implementation.
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Lead))
			return false;

		Lead other = (Lead) o;

		return Objects.equals(this.getFirstName(), other.getFirstName())
				&& Objects.equals(this.getLastName(), other.getLastName())
				&& Objects.equals(this.get_id(), other.get_id()) && Objects.equals(this.getEmail(), other.getEmail())
				&& Objects.equals(this.getAddress(), other.getAddress())
				&& Objects.equals(this.getEntryDate(), other.getEntryDate());
	}

	@Override
	public int hashCode() {

		return Objects.hash(this.firstName, this.lastName, this._id, this.email, this.address, this.entryDate);

	}

}
