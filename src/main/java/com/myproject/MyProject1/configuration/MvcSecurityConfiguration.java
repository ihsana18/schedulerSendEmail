package com.myproject.MyProject1.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {

	@Bean
    @Order(1)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/resources/**", "/user/login","/user/update","/user/list-user").permitAll()
			.antMatchers("/user/**","template/**","/scheduler/**","/recipient/**","/account/accessDenied").hasAnyAuthority("Administrator","Supervisor")
			.antMatchers("/template/list-template","/template/insert","/template/update",
					"/scheduler/list-scheduler","/scheduler/insert","/scheduler/update",
					"/recipient/list-recipient","/recipient/insert","/recipient/update"
					).hasAuthority("Data Entry")
			.anyRequest().permitAll()
			.and().formLogin()
			.loginPage("/account/loginForm")
			.loginProcessingUrl("/authenticating")
			.and().logout()
			.and().exceptionHandling().accessDeniedPage("/account/accessDenied");
		return http.build();
	}
}
