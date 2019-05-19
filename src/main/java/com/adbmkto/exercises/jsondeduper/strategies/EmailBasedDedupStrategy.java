package com.adbmkto.exercises.jsondeduper.strategies;

import java.util.HashMap;

import com.adbmkto.exercises.jsondeduper.models.Lead;

public class EmailBasedDedupStrategy extends AbstractDedupStrategy<String> {

	private HashMap<String, Lead> dedupTracker = new HashMap<String, Lead>();

	@Override
	public Lead getExistingDup(Lead lead) {

		return dedupTracker.get(lead.getEmail());

	}

	@Override
	public void markAsNonDup(Lead lead) {

		dedupTracker.put(lead.getEmail(), lead);
	}

}
