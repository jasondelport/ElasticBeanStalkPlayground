package com.jasondelport.springboot.demo;

import com.jasondelport.springboot.demo.component.AllStrategiesExampleBean;
import com.jasondelport.springboot.demo.config.AwsProperties;
import com.jasondelport.springboot.demo.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(AwsProperties.class)
public class DemoApplication {

	// https://www.baeldung.com/spring-core-annotations
	// https://www.baeldung.com/spring-boot-annotations

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	private AllStrategiesExampleBean allStrategiesExampleBean;

	@Autowired
	private Configuration configuration;

	@Autowired
	private Environment environment;

	// https://www.baeldung.com/spring-async
	// @Bean marks a factory method which instantiates a Spring bean: Spring calls
	// these methods when a new instance of the return type is required.
	// Note, that all methods annotated with @Bean must be in @Configuration classes.
	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("Async-");
		executor.initialize();
		return executor;
	}

	/*
	// https://www.baeldung.com/spring-core-annotations
	// https://www.baeldung.com/spring-bean-scopes
	@Lazy
	@Bean
	@Scope("singleton")
	public Person personSingleton() {
		return new Person();
	}
	*/

	// https://www.baeldung.com/running-setup-logic-on-startup-in-spring
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {

				log.info("Let's inspect the beans provided by Spring Boot:");

				String[] beanNames = ctx.getBeanDefinitionNames();
				Arrays.sort(beanNames);
				for (String beanName : beanNames) {
					log.info(beanName);
				}
			}

		};
	}
	*/


	@PostConstruct
	private void init(){
		log.info("Spring boot active profile via config: " + configuration.getName());
		log.info("Spring boot active profile: " + environment.getActiveProfiles().toString());
	}


}
