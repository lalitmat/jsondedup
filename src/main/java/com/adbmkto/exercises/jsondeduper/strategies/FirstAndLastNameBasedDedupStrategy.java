package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;

/***
 * First and Last Name based Deduping approach. Uses the
 * FirstAndLastNameDedupKey composite key as the key within the dedupTracker.
 * 
 * Added here as an example to indicate the options for future extension as the
 * lead schema changes and additional interesting attributes are added to the
 * schema that may be useful as de-duping strategies.
 * 
 * @author lalit
 *
 */
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
