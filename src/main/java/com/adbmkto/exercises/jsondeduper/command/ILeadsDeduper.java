package com.adbmkto.exercises.jsondeduper.command;

public interface ILeadsDeduper {

	public void dedup(String leadsSourceDir, String leadsSourceFileName, String... dedupStrategies)
			throws Exception;

}
