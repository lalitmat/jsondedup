package com.adbmkto.exercises.jsondeduper.command;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.serdeser.ILeadsSerDeserializer;
import com.adbmkto.exercises.jsondeduper.strategies.IDeduperStrategy;

@Component
public class LeadsDeDuper implements ILeadsDeduper, ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(LeadsDeDuper.class);

	private static final String dedupedPrefix = "DEDUPED.";

	@Autowired
	private ILeadsSerDeserializer leadsSerDeserializer;

	private ApplicationContext applicationContext;

	/*
	 * Iterate over Source List For each item in Source List
	 * 
	 * Check if id->lead map has an entry for same id (get(id)) // o(n) if yes, dup
	 * due to id the compare dates , if date in source >= date in map, put (id,
	 * source obj), debugdumpreplacement , else skip this object debugdumpskipdup,
	 * if no, no existing dup due to id, put (id, source obj) return valueSet from
	 * the map at the end
	 */
	@Override
	public void dedup(String leadsSourceFileDir, String leadsSourceFileName, String... dedupStrategies)
			throws Exception {

		LeadsCollection collectionToDedup = leadsSerDeserializer
				.deserializeCollection(leadsSourceFileDir + File.separator + leadsSourceFileName);

		for (String dedupStrategy : dedupStrategies) {
			LeadsCollection dedupedLeadsCollection = dedupUsingStrategy(collectionToDedup.getLeads(), dedupStrategy);
			collectionToDedup = dedupedLeadsCollection;
		}

		leadsSerDeserializer.serializeCollection(collectionToDedup,
				leadsSourceFileDir + File.separator + dedupedPrefix + leadsSourceFileName);

	}

	private LeadsCollection dedupUsingStrategy(List<Lead> leadsInSource, String dedupStrategy) throws Exception {

		IDeduperStrategy dedupStrategyProvider = applicationContext.getBean(dedupStrategy, IDeduperStrategy.class);

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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
