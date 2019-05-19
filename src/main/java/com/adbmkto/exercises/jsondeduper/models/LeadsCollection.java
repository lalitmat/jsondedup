package com.adbmkto.exercises.jsondeduper.models;

import java.util.List;

/**
 * A simple POJO based model for the LeadCollection wrapper object. Used by
 * frameworks like Jackson (or GSON or others) as a target for the
 * deserialization from a file containig JSON Lead representations.
 * 
 * No Jackson annotations added - preferring Jackson Defaults.
 * 
 * @author lalit
 *
 */
public class LeadsCollection {

	private List<Lead> leads;

	public List<Lead> getLeads() {
		return leads;
	}

	public void setLeads(List<Lead> leads) {
		this.leads = leads;
	}

}
