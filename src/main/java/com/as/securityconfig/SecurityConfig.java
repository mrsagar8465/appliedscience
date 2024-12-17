package com.as.securityconfig;


import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;




@Configuration
public class SecurityConfig {


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    // Disable CSRF and CORS
	    http.csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.disable())
	        // Configure authorization rules
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/user/add").permitAll().requestMatchers("/login").permitAll() // Allow login endpoint without authentication
	            .anyRequest().authenticated())            // All other requests require authentication
	        // Configure session management
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    return http.build();
	}

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("ankit")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public FilterRegistrationBean  coresFilter() {
        // Create a CORS configuration
    	 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        corsConfiguration.addAllowedOriginPattern("*");  // Allow all origins (adjust for your needs)
        corsConfiguration.addAllowedHeader("*");
     
        
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L);  // Cache the preflight response for 1 hour (3600 seconds)

        // Register the CORS configuration with the path pattern "/**"
        
        source.registerCorsConfiguration("/**", corsConfiguration);

        // Create a CorsFilter using the source and register it
        FilterRegistrationBean  bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(0);  // You can adjust the order to ensure it runs first
        return bean;
    }
}
    
    

