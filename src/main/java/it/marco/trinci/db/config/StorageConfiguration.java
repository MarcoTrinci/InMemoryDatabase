package it.marco.trinci.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.marco.trinci.db.service.StorageInterface;
import it.marco.trinci.db.service.impl.MapStorage;

@Configuration
public class StorageConfiguration {

	@Bean
	public StorageInterface getStorage() {
		return new MapStorage();
	}
	
}
