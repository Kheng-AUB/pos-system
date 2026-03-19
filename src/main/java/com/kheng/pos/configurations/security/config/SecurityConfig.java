package com.kheng.pos.configurations.security.config;

import com.kheng.pos.configurations.jwt.JwtValidator;
import com.kheng.pos.configurations.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public JwtValidator jwtValidator() {
        return new JwtValidator(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(manage ->
                        manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/api/super-admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .anyRequest().permitAll()
                ).headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) )
                .addFilterBefore(jwtValidator(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(
                        c -> c.configurationSource(cfgSource())
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource cfgSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Arrays.asList(
                    "http://localhost:5173",
                    "http://localhost:3000"
            ));

            cfg.setAllowedMethods(Collections.singletonList("*"));
            cfg.setAllowedHeaders(Collections.singletonList("*"));
            cfg.setAllowCredentials(true);
            cfg.setExposedHeaders(List.of("Authorization"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }
}
