package br.com.backendapi.setup;


import br.com.backendapi.domain.user.User;
import br.com.backendapi.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        try {
            userRepository.save(buildDefaultAdmin());
        } catch (Exception e) {}

    }

    private User buildDefaultAdmin() {

        User user = new User();

        user.setName("ADM");
        user.setCpf("000000000");
        user.setPassword(passwordEncoder.encode("123456"));

        return user;

    }

}
