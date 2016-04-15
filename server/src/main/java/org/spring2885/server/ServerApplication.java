package org.spring2885.server;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// Mustache takes over the view handler, do not want.
@EnableAutoConfiguration(exclude={MustacheAutoConfiguration.class})
@EnableJpaAuditing
@ComponentScan
@Controller
public class ServerApplication extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/formlogin").setViewName("login");
	}

	public static void main(String[] args) throws Exception {
	    // Disable the obnoxious ehcache update check.
	    System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
		new SpringApplicationBuilder(ServerApplication.class).run(args);  // $COVERAGE-IGNORE$
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private DataSource ds;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/api/**").fullyAuthenticated()
					.antMatchers("/user").fullyAuthenticated()
					.antMatchers("/").permitAll()
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
				.dataSource(ds)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select email, password, true from person where email = ?")
				.authoritiesByUsernameQuery("select p.id, ifnull(r.rolename, 'USER') from person p left join roles r on r.id=p.id WHERE p.email = ?")
				.rolePrefix("ROLE_")
				.configure(auth);
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
	}
	
}
