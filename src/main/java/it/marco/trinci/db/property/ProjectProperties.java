package it.marco.trinci.db.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
	
	private String name;
	private String description;
	private String version;
	
}
