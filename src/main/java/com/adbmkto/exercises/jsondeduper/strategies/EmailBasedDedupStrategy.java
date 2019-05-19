package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;

public class EmailBasedDedupStrategy extends AbstractDedupStrategy<String> {

	

	@Override
	public Lead getExistingDup(Lead lead) {

		return dedupTracker.get(lead.getEmail());

	}

	@Override
	public void markAsNonDup(Lead lead) {

		dedupTracker.put(lead.getEmail(), lead);
	}

}
