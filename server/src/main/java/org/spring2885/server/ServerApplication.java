package org.spring2885.server;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class ServerApplication extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(ServerApplication.class).run(args);
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
					.antMatchers("/").permitAll()
				// Enable form login at /login.  POST with "username" and "password"
				// as the form parameters needed.
				.and()
				.formLogin()
				.loginPage("/login").failureUrl("/login?error").permitAll()
				.and()
				.logout().logoutUrl("/logout").deleteCookies().invalidateHttpSession(true).logoutSuccessUrl("/")
				.and()
				// This makes the /logout URL work.
				// See https://github.com/spring-projects/spring-webflow-samples/issues/28
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
