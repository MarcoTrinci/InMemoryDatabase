package it.marco.trinci.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.marco.trinci.db.property.ProjectProperties;
import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {
	
	private final ProjectProperties projectProperties;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
	                .select()
			.apis(RequestHandlerSelectors.basePackage("it.marco.trinci.db"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(this.apiInfo())
			.useDefaultResponseMessages(false);
	}
	
	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(projectProperties.getName());
		apiInfoBuilder.description(projectProperties.getDescription());
		apiInfoBuilder.version(projectProperties.getVersion());
		return apiInfoBuilder.build();
	}
}
