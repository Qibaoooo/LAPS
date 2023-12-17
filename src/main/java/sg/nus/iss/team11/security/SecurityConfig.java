package sg.nus.iss.team11.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static String[] publicURLs = {
			// public MVC URLs:
			"/v1/about",       
			"/v1/home",
			"/v1/login",
			"/v1/login/**",
			// public REST API URLs:
			"/style.css",
			"/api/auth/login", 
			"/about",
			"/error"
	}; 

	private static String[] mvcStaffURLs = {
			"/v1/staff/**",
	};
	
	private static String[] mvcManagerURLs = {
			"/v1/manager/**",
	};
	
	private static String[] mvcAdminURLs = {
			"/v1/admin/**",
	};

	private static String[] reactStaffURLs = {
			"/api/staff/**",
	};

	private static String[] reactManagerURLs = {
			"/api/manager/**",
	};

	private static String[] reactAdminURLs = {
			"/api/admin/**",
	};


	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(this.encoder);

		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Autowired
	PasswordEncoder encoder;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> 
 					auth
 					.requestMatchers(publicURLs).permitAll()
 					
 					.requestMatchers(mvcStaffURLs).hasRole("staff")
 					.requestMatchers(mvcManagerURLs).hasRole("manager")
 					.requestMatchers(mvcAdminURLs).hasRole("admin")
 					
 					.requestMatchers(reactStaffURLs).hasRole("staff")
 					.requestMatchers(reactManagerURLs).hasRole("manager")
 					.requestMatchers(reactAdminURLs).hasRole("admin")
 					
 						.anyRequest().authenticated()
				);
		
		http.formLogin(form -> form.loginPage("/v1/login").permitAll());

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	

}