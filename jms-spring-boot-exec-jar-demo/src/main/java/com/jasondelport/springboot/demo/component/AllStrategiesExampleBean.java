package com.jasondelport.springboot.demo.component;

import com.jasondelport.springboot.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(value = "prototype")
public class AllStrategiesExampleBean implements InitializingBean {

	/*

	[main] INFO o.b.startup.AllStrategiesExampleBean - Constructor
	[main] INFO o.b.startup.AllStrategiesExampleBean - PostConstruct
	[main] INFO o.b.startup.AllStrategiesExampleBean - InitializingBean
	[main] INFO o.b.startup.AllStrategiesExampleBean - init-method

	 */

	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public AllStrategiesExampleBean() {
		LOG.info("Constructor");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("InitializingBean");
	}

	@PostConstruct
	public void postConstruct() {
		LOG.info("PostConstruct");
	}

	public void init() {
		LOG.info("init-method");
	}
}