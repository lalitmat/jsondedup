package com.adbmkto.exercises.jsondeduper.serdeser;

import java.io.File;

import org.springframework.stereotype.Component;

import com.adbmkto.exercises.jsondeduper.models.LeadsCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class JacksonLeadsSerDeserializer implements ILeadsSerDeserializer {

	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;

	public LeadsCollection deserializeCollection(String leadsSourceFilePath) throws Exception {

		LeadsCollection leads = mapper.readValue(new File(leadsSourceFilePath), LeadsCollection.class);
		return leads;

	}

	public void serializeCollection(LeadsCollection leads, String leadsDumpFilePath) throws Exception {

		mapper.writeValue(new File(leadsDumpFilePath), leads);

	}

	public String debug(Object obj) throws Exception {
		
		 return mapper.writeValueAsString(obj);
		 
	}

	

}