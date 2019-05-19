package com.adbmkto.exercises.jsondeduper.services;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.serdeser.ILeadsSerDeserializer;
import com.adbmkto.exercises.jsondeduper.strategies.IDeduperStrategy;

@Component
public class ServiceFacade implements ApplicationContextAware {

	/** Prefix added to the file with the deduped JSON output **/
	private static final String dedupedPrefix = "DEDUPED.";

	/**
	 * Sping applicationContext is made available to the Facade so as to allow
	 * different dedup strategy implementations to be pulled in on the fly
	 **/
	private ApplicationContext applicationContext;

	private static Logger LOG = LoggerFactory.getLogger(ServiceFacade.class);

	@Autowired
	/**
	 * Injecting an implementation for the main interface for JSON to Java
	 * De-serialization and Reverse Serialization functions
	 **/
	private ILeadsSerDeserializer leadsSerDeserializer;

	@Autowired
	/**
	 * Injecting a command implementation that handles the dedup based on a specific
	 * strategy
	 **/
	private IDeduplicationCmd deDupCmd;

	/**
	 * The directory where the source file containing JSON to be deduped is placed -
	 * injected from one of various Spring boot options such as
	 * application.properties, System properties , Cmd line args etc
	 **/
	@Value("${json.source.dir}")
	private String leadsSourceFileDir;

	/** The JSON file name with source data to be deduped **/
	@Value("${json.source.filename:leads.json}")
	private String leadsSourceFileName;

	/** The selected dedup strategies **/
	@Value("${dedup.strategies:id,email}")
	private String dedupStrategyNames;

	/**
	 * The entry point method for dedup implementation. Will iterate over the
	 * specified dedup strategies and dedup the source colection in multiple rounds
	 * Will serialize back the deduped JSON ito a file in the same directory as the
	 * source with the dedupedPrefix added to the file name.
	 * 
	 * @throws Exception
	 */
	public void dedup() throws Exception {

		LeadsCollection collectionToDedup = leadsSerDeserializer
				.deserializeCollection(leadsSourceFileDir + File.separator + leadsSourceFileName);

		collectionToDedup = deDuplicateLeadsWithSpecifiedStrategies(collectionToDedup, dedupStrategyNames);

		leadsSerDeserializer.serializeCollection(collectionToDedup,
				leadsSourceFileDir + File.separator + dedupedPrefix + leadsSourceFileName);

	}

	/**
	 * Given a deserialized Leads collection, and a list of comma separated strategy
	 * names, will perform deduplication with each by delegation to the
	 * deDupCommand.
	 * 
	 * @param collectionToDedup
	 * @param dedupStrategyNames
	 * @return
	 * @throws Exception
	 */

	private LeadsCollection deDuplicateLeadsWithSpecifiedStrategies(LeadsCollection collectionToDedup,
			String dedupStrategyNames) throws Exception {

		LOG.info("Before applying any Dedup Strategy, The collection is : ");
		LOG.info(leadsSerDeserializer.debug(collectionToDedup));

		String[] dedupStrategies = dedupStrategyNames.split(",");
		for (String dedupStrategy : dedupStrategies) {

			LOG.info("Applying Dedup Strategy of " + dedupStrategy);

			LeadsCollection dedupedLeadsCollection = dedupUsingSingleStrategy(collectionToDedup.getLeads(),
					dedupStrategy);
			collectionToDedup = dedupedLeadsCollection;

			LOG.info("After Dedup Strategy of " + dedupStrategy + " The collection is : ");
			LOG.info(leadsSerDeserializer.debug(collectionToDedup));

		}

		return collectionToDedup;

	}

	/**
	 * Performs Dedup for one single dedupStrategy. The DedupStrategy is a string
	 * that matches a Bean name that implements IDeduperStratgey as configured in
	 * the JsonDeDuperStrategyConfig.
	 * 
	 * @param leadsInSource
	 * @param dedupStrategy
	 * @return
	 * @throws Exception
	 */
	private LeadsCollection dedupUsingSingleStrategy(List<Lead> leadsInSource, String dedupStrategy) throws Exception {

		IDeduperStrategy dedupStrategyProvider = applicationContext.getBean(dedupStrategy, IDeduperStrategy.class);

		return deDupCmd.dedupWithStrategyProvider(leadsInSource, dedupStrategyProvider);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
