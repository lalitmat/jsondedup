package com.adbmkto.exercises.jsondeduper.strategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonDeDuperStrategyConfig {

	@Bean
	public IDeduperStrategy id() {
		return new IdBasedDedupStrategy();
	}

	@Bean
	public IDeduperStrategy email() {
		return new EmailBasedDedupStrategy();
	}

	@Bean
	public IDeduperStrategy firstandlastname() {
		return new FirstAndLastNameBasedDedupStrategy();
	}

}
