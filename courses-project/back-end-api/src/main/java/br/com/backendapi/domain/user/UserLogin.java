package br.com.courses.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(String email,
                        String cpf,
                        @NotBlank
                        String password) {
}
