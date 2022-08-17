package com.nieto.microservices.microservices.security.config;

import com.nieto.microservices.microservices.appuser.AppUser;
import com.nieto.microservices.microservices.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

	private final AppUserService appUserService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	protected  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       //Send post request without beeing disabled
        http
				.csrf().disable()
				.authorizeHttpRequests()
				.antMatchers("/api/v*/registration/**")
				.permitAll()
				.anyRequest().authenticated().and()
				.formLogin();
        return http.build();
	}

    @Bean
	protected AuthenticationManager authManager(HttpSecurity http,
												BCryptPasswordEncoder bCryptPasswordEncoder,
												AppUserService appUserService
												) throws Exception {
       return    http.getSharedObject(AuthenticationManagerBuilder .class)
				   .userDetailsService(appUserService)
				   .passwordEncoder(bCryptPasswordEncoder)
				   .and()
				   .build();
	}






}
