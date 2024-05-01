package com.example.bookingapp.configuration.security;

import com.example.bookingapp.controller.BaseUrl;
import com.example.bookingapp.security.UserDetailsServiceImpl;
import com.example.bookingapp.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
         httpSecurity.authorizeHttpRequests((auth) ->
                auth.requestMatchers(HttpMethod.POST,BaseUrl.BASE_URL + "user").permitAll()
                        .requestMatchers(BaseUrl.BASE_URL+"hotel/**").hasRole("ADMIN")
                        .requestMatchers(BaseUrl.BASE_URL+"room/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,BaseUrl.BASE_URL+"booking").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                 .logout(log-> log.clearAuthentication(true));

         return httpSecurity.build();

    }



}
