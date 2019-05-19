package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;

public class FirstAndLastNameBasedDedupStrategy extends AbstractDedupStrategy<FirstAndLastNameDedupKey> {

	@Override
	public Lead getExistingDup(Lead lead) {

		FirstAndLastNameDedupKey lookupKey = new FirstAndLastNameDedupKey(lead.getFirstName(), lead.getLastName());
		return dedupTracker.get(lookupKey);

	}

	@Override
	public void markAsNonDup(Lead lead) {

		FirstAndLastNameDedupKey lookupKey = new FirstAndLastNameDedupKey(lead.getFirstName(), lead.getLastName());
		dedupTracker.put(lookupKey, lead);
	}

}
