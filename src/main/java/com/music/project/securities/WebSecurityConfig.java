package com.music.project.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configure(http)) // Enable CORS
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("api/users/sign-in").permitAll()
                                .requestMatchers("api/test/**").permitAll()
                                .requestMatchers("error/**").permitAll()
                                .requestMatchers("api/users/sign-in",
                                        "api/users/register",
                                        "api/users/active-account",
                                        "api/users/request-otp",
                                        "api/users/verify-reset-password",
                                        "api/users/reset-password",
                                        "api/users/details/{id}"
                                )
                                .permitAll()
                                .requestMatchers("api/users/change-info").hasAnyRole("USER","ARTIST","ADMIN")
                                .requestMatchers("api/songs/**").permitAll()
                                .requestMatchers("api/playlist/**").permitAll()
                                .requestMatchers("api/artist-guess-access/**").permitAll()
                                .requestMatchers("api/files/**").permitAll()
                                .requestMatchers("/api/artist/**").hasAnyRole("ARTIST","ADMIN")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/album/add").hasAnyRole("ARTIST","ADMIN")
                                .requestMatchers("api/genre/findAllGenreOptions").permitAll()
                                .requestMatchers("api/genre/add").permitAll()
                                .anyRequest()
                                .authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

//        return http
//                .cors(cor -> cor.disable())
//                .csrf(cs -> cs.disable())
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(
//                                    "api/test/**"
//                            )
//                            .permitAll()
//                            .anyRequest()
//                            .authenticated();
//                })
//                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .build();
    }
}