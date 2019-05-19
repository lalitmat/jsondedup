package com.adbmkto.exercises.jsondeduper.serdeser;

import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

/**
 * An utility interface for serializing and de-serializing Lead collections
 * Jackson, GSON or other frameworks based implementations of this interface may
 * be created and injected to perform basic de-serialization of Leads from JSON
 * to Java, serializing back to JSON from Java and debug dumping of the Java
 * objects for logging.
 * 
 * @author lalit
 *
 */
public interface ILeadsSerDeserializer {

	/***
	 * Read a list of Leads in JSON format from a file into a LeadsCollection Model
	 * Java object.
	 * 
	 * @param leadsSourceFilePath
	 * @return
	 * @throws Exception
	 */
	public LeadsCollection deserializeCollection(String leadsSourceFilePath) throws Exception;

	/****
	 * Serialize back a list of JSON format Leads into a file.
	 * 
	 * @param leads
	 * @param leadsDumpFilePath
	 * @throws Exception
	 */
	public void serializeCollection(LeadsCollection leads, String leadsDumpFilePath) throws Exception;

	/***
	 * Convert to String the JSON representation of an Object for redirection to a
	 * Log transport.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String debug(Object obj) throws Exception;

}
