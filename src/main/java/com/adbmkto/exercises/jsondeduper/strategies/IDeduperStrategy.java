package com.adbmkto.exercises.jsondeduper.strategies;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

/**
 * A list of operations that would be implemented in a template for deduping
 * based on any selected strategy.
 * 
 * @author lalit
 *
 */
public interface IDeduperStrategy {

	public Lead getExistingDup(Lead lead);

	public void markAsNonDup(Lead lead);

	public LeadsCollection getDedupResult();

}
