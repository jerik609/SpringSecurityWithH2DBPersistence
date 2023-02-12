package com.example.SpringSecurityWithH2DBPersistence.service;

import com.example.SpringSecurityWithH2DBPersistence.repository.UserRepository;
import com.example.SpringSecurityWithH2DBPersistence.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

// https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html

@Service
@RequiredArgsConstructor
public class OurUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    OurUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        final var password = passwordEncoder.encode("aaa");
        final var user = User.withUsername("Admin01").password(password).roles("USER").build();
        inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (inMemoryUserDetailsManager.userExists(username)) {
            return inMemoryUserDetailsManager.loadUserByUsername(username);
        }
        final var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
        return new UserDetailsImpl(user);
    }

}
