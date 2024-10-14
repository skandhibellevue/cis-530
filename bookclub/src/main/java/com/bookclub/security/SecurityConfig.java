package com.bookclub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define the SecurityFilterChain bean for HTTP security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/wishlist/remove/**")  // Disable CSRF for the remove endpoint
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/monthly-books/remove/**")  // Disable CSRF for the remove endpoint
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/monthly-books/**").hasRole("ADMIN")  // Restrict access to ADMIN role
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page with ?logout parameter after logout
                        .permitAll()
                );

        return http.build();
    }

    // Define the PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Configure the in-memory user details service
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();

        // Create two users in memory
        var userDetailsManager = new InMemoryUserDetailsManager();

        var user = User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password(encoder.encode("adminpassword"))
                .roles("USER", "ADMIN")
                .build();

        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);

        return userDetailsManager;
    }

    // Define the AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);

        return authManagerBuilder.build();
    }
}