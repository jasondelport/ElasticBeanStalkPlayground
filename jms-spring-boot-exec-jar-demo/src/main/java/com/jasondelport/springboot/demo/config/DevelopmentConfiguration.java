package com.jasondelport.springboot.demo.config;

import org.springframework.context.annotation.Profile;
		import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "default"})
public class DevelopmentConfiguration implements Configuration {

	@Override
	public String getName() {
		return "development profile";
	}
}