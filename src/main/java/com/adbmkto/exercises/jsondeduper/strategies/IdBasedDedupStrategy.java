package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;

public class IdBasedDedupStrategy extends AbstractDedupStrategy<String> {

	@Override
	public Lead getExistingDup(Lead lead) {

		return dedupTracker.get(lead.get_id());

	}

	@Override
	public void markAsNonDup(Lead lead) {

		dedupTracker.put(lead.get_id(), lead);
	}

}
