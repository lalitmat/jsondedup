package com.adbmkto.exercises.jsondeduper.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

/**
 * Provides the default implementation for collecting back the deduped list and
 * the basic data structure for tracking and storing the deduped elements.
 * 
 * @author lalit
 *
 * @param <T>
 */
public abstract class AbstractDedupStrategy<T> implements IDeduperStrategy {

	/** The data structure that tracks the deduped Leads **/
	protected Map<T, Lead> dedupTracker = new HashMap<T, Lead>();

	/**
	 * A default implementation of the extraction of the results from the
	 * LeadCollection object after de-dup.
	 **/
	@Override
	public LeadsCollection getDedupResult() {

		LeadsCollection leadsCollectionDeDuped = new LeadsCollection();
		List<Lead> dedupedList = new ArrayList<Lead>(dedupTracker.values());
		leadsCollectionDeDuped.setLeads(dedupedList);

		return leadsCollectionDeDuped;
	}

}
