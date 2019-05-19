package com.adbmkto.exercises.jsondeduper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adbmkto.exercises.jsondeduper.services.ServiceFacade;

/**
 * The main entry point Spring Boot bootstrapper app that implements the
 * CommandLineRunner interface to allow a "non-web" app to be launched
 * 
 * @author lalit
 *
 */
@SpringBootApplication
public class JsonDeDuperApp implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(JsonDeDuperApp.class);

	/** Main Service Facade for accessing the dedup function **/
	@Autowired
	ServiceFacade leadsDeduperSvc;

	public static void main(String[] args)

	{
		LOG.info("STARTING THE JSON DEDUPER APPLICATION");
		SpringApplication.run(JsonDeDuperApp.class, args);
		LOG.info("THE JSON DEDUPER APPLICATION FINISHED");

	}

	@Override
	public void run(String... args) throws Exception {

		leadsDeduperSvc.dedup();

	}
}
