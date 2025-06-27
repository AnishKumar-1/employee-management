package com.management.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.management.jwt.CustomJwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //bean for securityFilter chain

    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",              // your login/register endpoints
            "/swagger-ui/**",            // Swagger UI resources
            "/v3/api-docs/**",           // OpenAPI docs endpoint
            "/swagger-resources/**",     // used by Swagger internally
            "/webjars/**",                // Swagger's static resources (JS/CSS)
            "/api/docs/**",
            "/h2-db/**"
    };



    @Autowired
	private CustomJwtFilter jwtFilter;
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF (useful for stateless APIs)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        WHITE_LIST_URL

                ).permitAll()  // Allow access to "/api/auth/**"
                .anyRequest().authenticated()  // Require authentication for other routes
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless authentication (JWT, etc.)
            )
            .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(customAuthenticationEntryPoint)  // Handles 401 Unauthorized
                    .accessDeniedHandler(customAccessDeniedHandler)  // Handles 403 Forbidden
                )
                .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .build();
    }

    //@password encoder for encoding passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
   
    //authenticationManager bean required to authenticate user
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

}
