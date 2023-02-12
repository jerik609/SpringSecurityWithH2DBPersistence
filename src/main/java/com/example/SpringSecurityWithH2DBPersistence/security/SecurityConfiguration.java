package com.example.SpringSecurityWithH2DBPersistence.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests -> {
            requests.requestMatchers("/test").hasRole("USER"); // protect the /test endpoint
            requests.requestMatchers("/h2/**").permitAll(); // TODO: make available only for special user of role DB_ADMIN
            requests.anyRequest().permitAll(); // enable all the other endpoints as free for all
        });
        httpSecurity.csrf().disable(); // disabling CSRF will allow sending POST request using Postman
        httpSecurity.headers().frameOptions().disable(); // H2 console needs this :-)
        httpSecurity.httpBasic(); // enable basic auth
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder myEncoder() {
        return new BCryptPasswordEncoder();
    }

}
