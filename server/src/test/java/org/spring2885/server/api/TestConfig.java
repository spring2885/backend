package org.spring2885.server.api;

import static org.mockito.Mockito.mock;

import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.PersonService;
import org.spring2885.server.db.service.PersonTypeService;
import org.spring2885.server.db.service.SocialServiceService;
import org.spring2885.server.db.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {
        "org.spring2885.server.api",
        "org.spring2885.server.model"
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
	public LanguageService languageServiceMock() {
	    return mock(LanguageService.class);
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
	
	@Bean
	public TokenService tokenStreamMock() {
	    return mock(TokenService.class);
	}
	
	@Configuration
	@EnableWebSecurity
	@EnableWebMvc 
	public static class TestSecurityConfig extends WebSecurityConfigurerAdapter {

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .usernameParameter("user")
	                .passwordParameter("pass")
	                .loginPage("/login")
	             .and()
	                .csrf().disable();
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	            .withUser("user").password("password").roles("USER");
	    }
	}
}
