package org.spring2885.server.api;

import static org.mockito.Mockito.mock;

import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.PersonService;
import org.spring2885.server.db.service.PersonTypeService;
import org.spring2885.server.db.service.SocialServiceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
		"org.spring2885.server.api"
})
public class TestConfig {
	@Bean
	public PersonService personServiceMock() {
		return mock(PersonService.class);
	}

	@Bean
	public PersonTypeService personTypeServiceMock() {
		return mock(PersonTypeService.class);
	}

	@Bean
	public SocialServiceService socialServiceMock() {
		return mock(SocialServiceService.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public JobService jobServiceMock() {
		return mock(JobService.class);
	}
	
	@Bean
	public JobTypeService jobTypeServiceMock() {
		return mock(JobTypeService.class);
	}
	
}
