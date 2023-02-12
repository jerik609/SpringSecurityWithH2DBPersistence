package com.example.SpringSecurityWithH2DBPersistence.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTO(@NotBlank String username, @NotNull String password, @NotBlank String role) {
}
