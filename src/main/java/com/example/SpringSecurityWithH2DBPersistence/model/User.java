package com.example.SpringSecurityWithH2DBPersistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// https://stackoverflow.com/questions/60769689/problems-with-generatedvalue-in-the-h2-database

// https://stackoverflow.com/questions/48318097/is-it-possible-to-make-lomboks-builder-public
// https://stackoverflow.com/questions/49168589/is-lomboks-builder-thread-safe

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "\"user\"")
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String username;

    @ToString.Exclude
    private String password;

    private String role;

    @ToString.Exclude
    private boolean enabled;

}
