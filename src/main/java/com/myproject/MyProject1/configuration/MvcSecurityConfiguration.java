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
			.antMatchers("/resources/**", "/account/index","/recipient/index","/template/index",
					"/scheduler/index","/recipient/upsertForm","/recipient/upsert","/recipient/delete").permitAll()
			.antMatchers("/account/registerForm","/account/register","/account/delete"
					,"/template/upsertForm","/template/delete","/template/upsert"
					,"/scheduler/upsertForm","/scheduler/upsert","/scheduler/delete"

					).hasAnyAuthority("Administrator","Supervisor")
			.antMatchers().hasAuthority("Data Entry")
			.anyRequest().permitAll()
			.and().formLogin()
			.loginPage("/account/loginForm")
			.loginProcessingUrl("/authenticating")
			.and().logout()
			.and().exceptionHandling().accessDeniedPage("/account/accessDenied");
		return http.build();
	}
}
