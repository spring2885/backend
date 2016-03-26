package org.spring2885.server.api;

import static org.mockito.Mockito.mock;

import org.spring2885.server.api.utils.RequestHelper;
import org.spring2885.server.api.utils.RequestHelperImpl;
import org.spring2885.server.db.model.JobConverters;
import org.spring2885.server.db.model.JobTypeConverters;
import org.spring2885.server.db.model.NewsConverters;
import org.spring2885.server.db.model.PersonConverters;
import org.spring2885.server.db.model.PersonTypeConverter;
import org.spring2885.server.db.model.SocialServiceConverters;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.NewsService;
import org.spring2885.server.db.service.TokenService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.spring2885.server.db.service.person.SocialServiceService;
import org.spring2885.server.db.service.search.SearchParser;
import org.spring2885.server.mail.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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

@Import({ 
    NewsConverters.class, 
    JobConverters.class,
    JobTypeConverters.class,
    PersonConverters.class, 
    PersonTypeConverter.class,
    SocialServiceConverters.class
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
	
    @Bean
    public NewsService newsServiceMock() {
        return mock(NewsService.class);
    }
    
    @Bean Mailer mailerMock() {
        return mock(Mailer.class);
    }
    
    @Bean
    SearchParser searchParser() {
        return new SearchParser();
    }
    
    @Bean
    RequestHelper requestHelper() {
        return new RequestHelperImpl();
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
