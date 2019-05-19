package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;

/***
 * Id based Deduping approach. Uses Id as the key within the dedupTracker.
 * 
 * @author lalit
 *
 */
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
