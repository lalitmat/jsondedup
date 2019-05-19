package com.adbmkto.exercises.jsondeduper.services;

import java.util.List;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.strategies.IDeduperStrategy;

/**
 * Interface exposing the function to perform Deduplication with a selected
 * strategy. Defined as such so that in the future other strategies can be added
 * if needed.
 * 
 * For example, if the lead domain has other fields added in the future that may
 * make good candidates for de-dup determiation, a new implementation for this
 * interface may be created and configured via the JsonDeDuperStrategyConfig.
 * 
 * Test with dedup.strategies=email,id,firstandlastname in the
 * application.properties
 * 
 * As an example see FirstAndLastNameBasedDedupStrategy.
 * 
 * @author lalit
 *
 */
public interface IDeduplicationCmd {

	public LeadsCollection dedupWithStrategyProvider(List<Lead> leadsInSource, IDeduperStrategy dedupStrategyProvider)
			throws Exception;

}
