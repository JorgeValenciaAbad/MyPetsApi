package it.jorge.protectora;

import it.jorge.protectora.config.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class ProtectorApplication {

	private static final String[] AUTH_WHITE_LIST = {
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/v2/api-docs/**",
			"/swagger-resources/**"
	};
	public static void main(String[] args) {
		SpringApplication.run(ProtectorApplication.class, args);

	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
					.antMatchers(HttpMethod.POST, "/api/user/adduser").permitAll()
					.antMatchers(AUTH_WHITE_LIST).permitAll()
					.anyRequest().authenticated();
		}
	}
}
