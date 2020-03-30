package com.jasondelport.springboot.demo.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod"})
public class ProductionConfiguration implements Configuration {

	@Override
	public String getName() {
		return "production profile";
	}
}