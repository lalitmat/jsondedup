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

	private static final String dedupedPrefix = "DEDUPED.";

	private ApplicationContext applicationContext;

	private static Logger LOG = LoggerFactory.getLogger(ServiceFacade.class);

	@Autowired
	private ILeadsSerDeserializer leadsSerDeserializer;

	@Autowired
	private IDeduplicationCmd deDupCmd;

	@Value("${json.source.dir}")
	private String leadsSourceFileDir;

	@Value("${json.source.filename:leads.json}")
	private String leadsSourceFileName;

	@Value("${dedup.strategies:id,email}")
	private String dedupStrategyNames;

	public void dedup() throws Exception {

		LeadsCollection collectionToDedup = leadsSerDeserializer
				.deserializeCollection(leadsSourceFileDir + File.separator + leadsSourceFileName);

		collectionToDedup = deDuplicateLeadsWithSpecifiedStrategies(collectionToDedup, dedupStrategyNames);

		leadsSerDeserializer.serializeCollection(collectionToDedup,
				leadsSourceFileDir + File.separator + dedupedPrefix + leadsSourceFileName);

	}

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

	private LeadsCollection dedupUsingSingleStrategy(List<Lead> leadsInSource, String dedupStrategy) throws Exception {

		IDeduperStrategy dedupStrategyProvider = applicationContext.getBean(dedupStrategy, IDeduperStrategy.class);

		return deDupCmd.dedupWithStrategyProvider(leadsInSource, dedupStrategyProvider);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
