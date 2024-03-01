package com.joes.MySecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin = User
                .withUsername("admin")
                .password(encoder.encode("1"))
                .roles("ADMIN", "USER")
                .authorities("BASIC, SPECIAL")
                .build();


        UserDetails user = User
                .withUsername("user")
                .password(encoder.encode("2"))
                .roles("USER")
                .authorities("BASIC")
                .build();

        return new InMemoryUserDetailsManager(admin, user);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/open")

//                        .permitAll()
                                .requestMatchers(HttpMethod.GET, "/special").hasAuthority("SPECIAL")
                                .requestMatchers(HttpMethod.GET, "/special").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/special").hasAnyAuthority("SPECIAL", "BASIC")
                                .requestMatchers(HttpMethod.GET, "/basic").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/closed")
                        .authenticated()

                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


}
