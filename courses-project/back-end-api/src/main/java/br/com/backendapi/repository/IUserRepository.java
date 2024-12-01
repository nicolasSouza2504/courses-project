package br.com.backendapi.repository;

import br.com.backendapi.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByCpf(String cpfCnpj);

    Optional<User> findById(Long id);

    boolean existsByCpf(String cpf);

}
