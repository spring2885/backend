package org.spring2885.server.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void init(WebSecurity web) {
        web.ignoring().anyRequest();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.err.println("WebSecurityConfiguration.configure");
        http.antMatcher("/server/foo/**").authorizeRequests().anyRequest().authenticated();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	MysqlDataSource ds = new MysqlDataSource();
    	ds.setUrl("jdbc:mysql://localhost:3306/backend");
    	ds.setUser("backend");
    	ds.setPassword("backend");
    	auth.jdbcAuthentication()
    		.dataSource(ds)
    		.usersByUsernameQuery("select person_email, password_sha, true from person where person_email = ?")
    		.authoritiesByUsernameQuery("select person_email, 'ROLE_USER' from person where person_email = ?")
    		.configure(auth);
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
}