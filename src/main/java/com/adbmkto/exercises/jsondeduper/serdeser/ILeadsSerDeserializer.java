package com.adbmkto.exercises.jsondeduper.serdeser;

import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;

public interface ILeadsSerDeserializer {

	public LeadsCollection deserializeCollection(String leadsSourceFilePath) throws Exception;

	public void serializeCollection(LeadsCollection leads, String leadsDumpFilePath) throws Exception;

	public String debug(Object obj) throws Exception;

}
