package com.adbmkto.exercises.jsondeduper.services;

import java.util.List;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.strategies.IDeduperStrategy;

public interface IDeduplicationCmd {

	public LeadsCollection dedupWithStrategyProvider(List<Lead> leadsInSource, IDeduperStrategy dedupStrategyProvider)
			throws Exception;

}
