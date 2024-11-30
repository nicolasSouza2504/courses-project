package br.com.courses.setup;


import br.com.courses.domain.user.User;
import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.repository.IUserRepository;
import br.com.courses.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        try {
            userRepository.save(buildDefaultAdmin());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private User buildDefaultAdmin() {

        User user = new User();

        user.setName("ADM");
        user.setCpf("000000000");
        user.setPassword(passwordEncoder.encode("123456"));

        return user;

    }

}
