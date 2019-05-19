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
public class DeDuplicationCommand implements IDeduplicationCmd {

	private static Logger LOG = LoggerFactory.getLogger(DeDuplicationCommand.class);

	@Autowired
	private ILeadsSerDeserializer leadsSerDeserializer;

	public ILeadsSerDeserializer getLeadsSerDeserializer() {
		return leadsSerDeserializer;
	}

	public void setLeadsSerDeserializer(ILeadsSerDeserializer leadsSerDeserializer) {
		this.leadsSerDeserializer = leadsSerDeserializer;
	}


	public LeadsCollection dedupWithStrategyProvider(List<Lead> leadsInSource, IDeduperStrategy dedupStrategyProvider)
			throws Exception {

		if (leadsInSource != null) {
			for (Lead lead : leadsInSource) {
				Lead existingDup = dedupStrategyProvider.getExistingDup(lead);
				if (existingDup != null) {

					if (lead.getEntryDate().after(existingDup.getEntryDate())
							|| lead.getEntryDate().equals(existingDup.getEntryDate())) {
						dedupStrategyProvider.markAsNonDup(lead);
						recordLeadReplacement(existingDup, lead);

					} else {
						recordDuplicateLeadSkip(existingDup, lead);
					}

				} else {
					dedupStrategyProvider.markAsNonDup(lead);
				}

			}
		}

		return dedupStrategyProvider.getDedupResult();

	}

	private void recordLeadDumpStartFinish() {
		LOG.info("****************************");
	}

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
