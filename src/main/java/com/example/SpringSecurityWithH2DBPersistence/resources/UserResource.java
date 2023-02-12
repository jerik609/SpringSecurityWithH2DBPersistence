package com.example.SpringSecurityWithH2DBPersistence.resources;

import com.example.SpringSecurityWithH2DBPersistence.model.User;
import com.example.SpringSecurityWithH2DBPersistence.model.UserDTO;
import com.example.SpringSecurityWithH2DBPersistence.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/*
POST:
{
    "username": "Batman",
    "password": "batman",
    "role": "ROLE_USER"
}
 */

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public ResponseEntity<Collection<UserDTO>> getUsers() {
        final var users = userRepository.findAll().stream().map(user -> UserDTO.builder()
                        .username(user.getUsername())
                        .password("<hidden>")
                        .role(user.getRole())
                        .build())
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        final var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        final var userDTO = UserDTO.builder()
                .username(user.getUsername())
                .password("<hidden>")
                .role(user.getRole())
                .build();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) {
        final var user = User.builder()
                .username(userDTO.username())
                .password(passwordEncoder.encode(userDTO.password()))
                .role(userDTO.role())
                .enabled(true)
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("User: " + user + " registered", HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
