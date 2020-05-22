package com.Hulk_Store;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMVC implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath= Paths.get("fotos").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/fotos/**")
		.addResourceLocations(resourcePath);
	}

	
	
	
}
