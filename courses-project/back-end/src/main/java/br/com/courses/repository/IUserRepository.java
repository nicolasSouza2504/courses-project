package br.com.courses.repository;

import br.com.courses.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByCpf(String cpfCnpj);

    Optional<User> findById(Long id);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

}
