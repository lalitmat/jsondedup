package com.adbmkto.exercises.jsondeduper;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adbmkto.exercises.jsondeduper.command.ILeadsDeduper;

@SpringBootApplication
public class JsonDeDuperApp implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(JsonDeDuperApp.class);

	@Autowired
	ILeadsDeduper leadsDeduper;

	public static void main(String[] args)

	{
		LOG.info("STARTING THE JSON DEDUPER APPLICATION");
		SpringApplication.run(JsonDeDuperApp.class, args);
		LOG.info("THE JSON DEDUPER APPLICATION FINISHED");

	}

	@Override
	public void run(String... args) throws Exception {

		// TODO: Perform args validation

		String leadsSourceDirPath = args[0];
		String leadsSourceFileName = args[1];
		String[] dedupStrategies = Arrays.copyOfRange(args, 2, args.length);

		leadsDeduper.dedup(leadsSourceDirPath, leadsSourceFileName, dedupStrategies);

	}
}
