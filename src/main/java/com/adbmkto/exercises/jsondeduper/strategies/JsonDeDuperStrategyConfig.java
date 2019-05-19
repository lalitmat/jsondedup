package com.adbmkto.exercises.jsondeduper.strategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Spring Boot Configuration class that injects singleton (by default),
 * eager loaded bean instances that implement the different supported dedup
 * strategies. The Bean names are by default the method names that are annotated
 * with @Bean. A comma separated list of these Bean names can be specified as
 * strategies in the dedup.strategies property to ensure that those strategies
 * are used for de-duping.
 * 
 * @author lalit
 *
 */
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
