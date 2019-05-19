package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

public interface IDeduperStrategy {

	public Lead getExistingDup(Lead lead);

	public void markAsNonDup(Lead lead);

	public LeadsCollection getDedupResult();

}
