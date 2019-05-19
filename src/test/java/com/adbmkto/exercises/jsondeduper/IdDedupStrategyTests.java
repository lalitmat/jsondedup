package com.adbmkto.exercises.jsondeduper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adbmkto.exercises.jsondeduper.models.Lead;
import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.adbmkto.exercises.jsondeduper.serdeser.JacksonLeadsSerDeserializer;
import com.adbmkto.exercises.jsondeduper.services.DeDuplicationCommand;
import com.adbmkto.exercises.jsondeduper.strategies.IdBasedDedupStrategy;

public class IdDedupStrategyTests {

	private static Lead johnSmithLead;
	private static Lead tedJonesLead;
	private static Lead joeMillerLead;
	private static Lead tedJones2Lead;

	private static DeDuplicationCommand deDupCmd = new DeDuplicationCommand();

	private static Logger LOG = LoggerFactory.getLogger(IdDedupStrategyTests.class);

	private static Lead buildLead(String _id, String email, String firstName, String lastName, String address,
			String entryDateStr) throws Exception {

		Lead lead = new Lead();
		lead.set_id(_id);
		lead.setEmail(email);
		lead.setFirstName(firstName);
		lead.setLastName(lastName);
		lead.setAddress(address);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date entryDate = format.parse(entryDateStr);
		lead.setEntryDate(entryDate);
		return lead;

	}

	@BeforeClass
	public static void setup() {
		LOG.info("startup - creating IdDedupStrategyTests harness");
		deDupCmd.setLeadsSerDeserializer(new JacksonLeadsSerDeserializer());

		try {

			johnSmithLead = buildLead("jkj238238jdsnfsj23", "foo@bar.com", "John", "Smith", "123 Street St",
					"2014-05-07 17:29:20");

			tedJonesLead = buildLead("jkj238238jdsnfsj23", "coo@bar.com", "Ted", "Jones", "456 Neat St",
					"2014-05-07 17:32:20");

			joeMillerLead = buildLead("mnm238238jdsnfsw21", "foo@bar.com", "Joe", "Miller", "234 True st",
					"2014-05-07 17:30:20");

			tedJones2Lead = buildLead("jkj238238jdsnfsj23", "coo@bar.com", "Ted2", "Jones2", "456 Neat2 St",
					"2014-05-07 17:32:20");

		} catch (

		Exception ex) {

			ex.printStackTrace();
			LOG.error("Exception during test harness set up : " + ex);
			System.exit(-1);

		}
	}

	@AfterClass
	public static void tearDown() {

		LOG.info("teardown - clearing the harness");

		johnSmithLead = null;
		tedJonesLead = null;
		joeMillerLead = null;
	}

	// Empty source , Id strategy => Empty Return
	@Test
	public void when_noElementInSource_ReturnSame() {

		try {

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(new ArrayList<Lead>(),
					new IdBasedDedupStrategy());

			assertTrue(dedupedCollection.getLeads() != null);
			assertTrue(dedupedCollection.getLeads().size() == 0);

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_noElementInSource_ReturnSame should not have thrown any exception!");
		}

	}

	// Source with 1 element, Id strategy => Return same as Source
	@Test
	public void when_oneElementInSource_ReturnSame() {

		try {

			List<Lead> leadsToDedup = new ArrayList<Lead>();
			leadsToDedup.add(johnSmithLead);

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(leadsToDedup,
					new IdBasedDedupStrategy());

			assertTrue(dedupedCollection.getLeads() != null);
			assertTrue(dedupedCollection.getLeads().size() == 1);
			assertTrue(dedupedCollection.getLeads().contains(johnSmithLead));

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_oneElementInSource_ReturnSame should not have thrown any exception!");
		}

	}

	// Source with 2 elements, Id strategy, both with different Ids, Id strategy =>
	// Return same as source
	@Test
	public void when_twoElementsDifferentIds_ReturnSame() {

		try {

			List<Lead> leadsToDedup = new ArrayList<Lead>();
			leadsToDedup.add(johnSmithLead);
			leadsToDedup.add(joeMillerLead);

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(leadsToDedup,
					new IdBasedDedupStrategy());
			assertTrue(dedupedCollection.getLeads().size() == 2);
			assertTrue(dedupedCollection.getLeads().contains(johnSmithLead));
			assertTrue(dedupedCollection.getLeads().contains(joeMillerLead));

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_twoElementsDifferentIds_ReturnSame should not have thrown any exception!");
		}

	}

	// Source with 2 elements, Id strategy, same Ids, first with later date =>
	// Return single element with first element
	@Test
	public void when_twoElementsSameIdsFirstLaterDate_ReturnFirst() {

		try {

			List<Lead> leadsToDedup = new ArrayList<Lead>();
			leadsToDedup.add(tedJonesLead);
			leadsToDedup.add(johnSmithLead);

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(leadsToDedup,
					new IdBasedDedupStrategy());
			assertTrue(dedupedCollection.getLeads().size() == 1);
			assertTrue(dedupedCollection.getLeads().contains(tedJonesLead));

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_twoElementsSameIdsFirstLaterDate_ReturnFirst should not have thrown any exception!");
		}

	}

	// Source with 2 elements, Id strategy, same Ids, second with later date =>
	// Return single element with second element
	@Test
	public void when_twoElementsSameIdsSecondLaterDate_ReturnSecond() {

		try {

			List<Lead> leadsToDedup = new ArrayList<Lead>();
			leadsToDedup.add(johnSmithLead);
			leadsToDedup.add(tedJonesLead);

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(leadsToDedup,
					new IdBasedDedupStrategy());
			assertTrue(dedupedCollection.getLeads().size() == 1);
			assertTrue(dedupedCollection.getLeads().contains(tedJonesLead));

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_twoElementsSameIdsSecondLaterDate_ReturnSecond should not have thrown any exception!");
		}

	}

	// Source with 2 elements, Id strategy, same Ids, both with same date => Return
	// second element
	@Test
	public void when_twoElementsSameIdsSameDate_ReturnSecond() {
		
		try {

			List<Lead> leadsToDedup = new ArrayList<Lead>();
			leadsToDedup.add(tedJonesLead);
			leadsToDedup.add(tedJones2Lead);

			LeadsCollection dedupedCollection = deDupCmd.dedupWithStrategyProvider(leadsToDedup,
					new IdBasedDedupStrategy());
			assertTrue(dedupedCollection.getLeads().size() == 1);
			assertTrue(dedupedCollection.getLeads().contains(tedJones2Lead));

		} catch (Exception e) {

			e.printStackTrace();
			fail("when_twoElementsSameIdsSameDate_ReturnSecond should not have thrown any exception!");
		}

	}

	// No Testing for cases where Id is missing. See stated assumptions wrt Id
	// always assumed to be present

}