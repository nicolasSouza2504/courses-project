package br.com.courses.repository;

import br.com.courses.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(@NotBlank(message = "Nome de Usuario deve ser preenchido") @Email(message = "Email deve ser Válido!") String email);

}
