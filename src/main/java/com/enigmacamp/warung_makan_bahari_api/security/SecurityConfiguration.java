package com.enigmacamp.warung_makan_bahari_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
//        return  http.httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(cfg ->cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(request ->
//                        request.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
//                                .requestMatchers("api/v1/auth/**","api/v1/image/**" , "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csfr -> csfr.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/menus", "/api/v1/auth/**", "/api/v1/tables").permitAll()
                        .anyRequest().authenticated());
//                .httpBasic(withDefaults());
        return http.build();
    }
}
