package com.adbmkto.exercises.jsondeduper.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

public abstract class AbstractDedupStrategy<T> implements IDeduperStrategy {

	protected HashMap<T, Lead> dedupTracker = new HashMap<T, Lead>();

	@Override
	public LeadsCollection getDedupResult() {

		LeadsCollection leadsCollectionDeDuped = new LeadsCollection();
		List<Lead> dedupedList = new ArrayList<Lead>(dedupTracker.values());
		leadsCollectionDeDuped.setLeads(dedupedList);

		return leadsCollectionDeDuped;
	}

}
