package com.adbmkto.exercises.jsondeduper.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.serdeser.ILeadsSerDeserializer;
import com.adbmkto.exercises.jsondeduper.strategies.IDeduperStrategy;

@Component
/**
 * Implementation of a command to perform de-duplication using a specific
 * strategy
 * 
 * @author lalit
 *
 */
public class DeDuplicationCommand implements IDeduplicationCmd {

	private static Logger LOG = LoggerFactory.getLogger(DeDuplicationCommand.class);

	/**
	 * Autowired an utility class for serializing and deserializing Java/JSON tree
	 * for debug/logging and final output
	 **/
	@Autowired
	private ILeadsSerDeserializer leadsSerDeserializer;

	public ILeadsSerDeserializer getLeadsSerDeserializer() {
		return leadsSerDeserializer;
	}

	public void setLeadsSerDeserializer(ILeadsSerDeserializer leadsSerDeserializer) {
		this.leadsSerDeserializer = leadsSerDeserializer;
	}

	/***
	 * 
	 * The implementation of the method that deduplicates a source list of Leads
	 * using a selected StrategyProvider
	 */
	public LeadsCollection dedupWithStrategyProvider(List<Lead> leadsInSource, IDeduperStrategy dedupStrategyProvider)
			throws Exception {

		if (leadsInSource != null) {
			// Iterate over leads in the source and for each
			for (Lead lead : leadsInSource) {

				// check if the source lead is already recorded in an internal structure within
				// the dedupStrategyProvider
				// At this point =, its expected that id or email (or some other combination of
				// attributes in the future)
				// will be used to determine if the source element has a known duplicate that's
				// been recorded already
				Lead existingDup = dedupStrategyProvider.getExistingDup(lead);

				// if a known matched dup is found
				if (existingDup != null) {

					// perform date based determination of which element stays in the deduped
					// structure if existing element in the list has a later date , then it stays
					// if existing element in the list has same date , even then it stays since it
					// comes later in the source list
					if (lead.getEntryDate().after(existingDup.getEntryDate())
							|| lead.getEntryDate().equals(existingDup.getEntryDate())) {

						dedupStrategyProvider.markAsNonDup(lead);
						recordLeadReplacement(existingDup, lead);

					} else {
						// If the existing element in the list has an earlier date, then we simply
						// record the fact that the current element in the source list has a duplicate
						// and that the current element is being skipped
						recordDuplicateLeadSkip(existingDup, lead);
					}

				} else {

					// If a known matched dup is not found, we import the current element as deduped
					// in the dedupStrategyProvider - if a dedup is found later in the source list
					// for the
					// current element, this element will be at that time be replaced
					dedupStrategyProvider.markAsNonDup(lead);
				}

			}
		}

		// Returning the final list of deduped JSON after deduping
		// Its assumed that the Order of the deduped array doesnt have to
		// preserve the original source array order
		return dedupStrategyProvider.getDedupResult();

	}

	private void recordLeadDumpStartFinish() {
		LOG.info("****************************");
	}

	/***
	 * Recording the source and Deduped matched element for the case where a source
	 * element is skipped from being added to the De-duped list
	 * 
	 * @param existingDup
	 * @param lead
	 * @throws Exception
	 */
	private void recordDuplicateLeadSkip(Lead existingDup, Lead lead) throws Exception {

		recordLeadDumpStartFinish();
		LOG.info("Not adding a Lead - for which there's a duplicate "
				+ "- to the De-duped List due to Entry Date conditions check");
		LOG.info("Lead being skipped: ");
		LOG.info(leadsSerDeserializer.debug(lead));
		LOG.info("Lead being added : ");
		LOG.info(leadsSerDeserializer.debug(existingDup));
		recordLeadDumpStartFinish();

	}

	/***
	 * Recording the From and To cases for a replacement of an element from the
	 * De-duped list
	 * 
	 * @param existingDup
	 * @param lead
	 * @throws Exception
	 */
	private void recordLeadReplacement(Lead existingDup, Lead lead) throws Exception {

		recordLeadDumpStartFinish();
		LOG.info("Replacing an Existing Lead in the De-duped List due " + "to Entry Date conditions check");
		LOG.info("Before replacement attributes: ");
		LOG.info(leadsSerDeserializer.debug(existingDup));
		LOG.info("After Replacement attributes: ");
		LOG.info(leadsSerDeserializer.debug(lead));
		recordLeadDumpStartFinish();

	}

}
