package br.com.backendapi.domain.user;

import java.util.Date;

public record UserRegisterDTO(String name, String email, String cpf, Date birthDate, String password) {

}
