package com.adbmkto.exercises.jsondeduper.serdeser;

import java.io.File;

import org.springframework.stereotype.Component;

import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

/**
 * Jackson based implementation of the ILeadsSerDeserializer interface that
 * handles the serialization and deserialization from/to the Leads JSON and
 * model POJOs defined within the com.adbmkto.exercises.jsondeduper.models
 * package.
 * 
 * @author lalit
 *
 */
@Component
public class JacksonLeadsSerDeserializer implements ILeadsSerDeserializer {

	/**
	 * The single ObjectMapper instance used throughout the app.
	 * 
	 * Object indentation enabled for the Debug.
	 * 
	 * Dates to be written as Timestamps turned off so that number of milliseconds
	 * since epoch are not dumped out in the serialization process for entryDate.
	 * 
	 * Date format set to the Standard ISO 8601 format with Colon in timezone.
	 */
	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

	/***
	 * Read a list of Leads in JSON format from a file into a LeadsCollection Model
	 * Java object.
	 * 
	 * @param leadsSourceFilePath
	 * @return
	 * @throws Exception
	 */
	public LeadsCollection deserializeCollection(String leadsSourceFilePath) throws Exception {

		LeadsCollection leads = mapper.readValue(new File(leadsSourceFilePath), LeadsCollection.class);
		return leads;

	}

	/****
	 * Serialize back a list of JSON format Leads into a file.
	 * 
	 * @param leads
	 * @param leadsDumpFilePath
	 * @throws Exception
	 */
	public void serializeCollection(LeadsCollection leads, String leadsDumpFilePath) throws Exception {

		mapper.writeValue(new File(leadsDumpFilePath), leads);

	}

	/***
	 * Convert to String the JSON representation of an Object for redirection to a
	 * Log transport.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String debug(Object obj) throws Exception {

		return mapper.writeValueAsString(obj);

	}

}
