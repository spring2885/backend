package org.spring2885.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalAuthentication
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.err.println("WebSecurityConfiguration.configure");
        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()
        	.and().httpBasic()
//        		.formLogin()
//        		.loginPage("/login")
//        		.loginProcessingUrl("/login")
//        		.usernameParameter("username")
//        		.passwordParameter("password")
        	.and().logout().logoutSuccessUrl("/login?logout")
        	.and().exceptionHandling().accessDeniedPage("/403")
        	.and().csrf();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("user").password("welcome").roles("USER");
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	MysqlDataSource ds = new MysqlDataSource();
//    	ds.setUrl("jdbc:mysql://localhost:3306/backend");
//    	ds.setUser("backend");
//    	ds.setPassword("backend");
//    	auth.jdbcAuthentication()
//    		.dataSource(ds)
//    		.usersByUsernameQuery("select person_email, password_sha, true from person where person_email = ?")
//    		.authoritiesByUsernameQuery("select person_email, 'ROLE_USER' from person where person_email = ?")
//    		.configure(auth);
    	auth.inMemoryAuthentication().withUser("user").password("welcome").roles("USER");
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
}