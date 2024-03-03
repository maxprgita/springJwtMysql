package it.aips.securityJWT.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.aips.securityJWT.security.JwtAuthenticationFilter;

//@EnableOAuth2Sso
@Configuration
@EnableWebSecurity
public class SecurityConfig { // extends WebSecurityConfigurerAdapter{

	@Value("${nutri.base.url}")
	private String baseUrl;

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.authenticationProvider = authenticationProvider;
	}

	/*
	 * @Bean public AuthenticationManager authenticationManager() throws Exception {
	 * return new AuthenticationManagerBuilder() .inMemoryAuthentication()
	 * .withUser("user").password("password").roles("USER") .and() .build(); }
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
	 * STATELESS) .and() .addFilterBefore(new CustomJwtTokenValidationFilter(),
	 * BasicAuthenticationFilter.class) .addFilterAfter(new
	 * CustomJwtTokenCreatorFilter(), BasicAuthenticationFilter.class)
	 * .authorizeRequests() .requestMatchers(baseUrl + "/info").authenticated()
	 * .requestMatchers(baseUrl + "/registrazione").permitAll()
	 * .requestMatchers(baseUrl + "/login").permitAll() .requestMatchers(baseUrl +
	 * "/getRoles").hasAuthority("ROLE_WRITE") .and()
	 * .httpBasic().and().csrf().disable(); }
	 */
/*	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} */

}
