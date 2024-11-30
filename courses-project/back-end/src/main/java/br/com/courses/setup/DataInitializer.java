package br.com.courses.setup;


import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserService iUserService;

    @Override
    public void run(String... args) throws Exception {

        try {
            createUser();

        } catch (Exception e) {}

    }

    public void createUser() {

        UserRegisterDTO userRegister = buildDefaultAdmin();

        iUserService.createUser(userRegister);

    }

    private UserRegisterDTO buildDefaultAdmin() {

        return new UserRegisterDTO("admin adm",
                "admin@gmail.com",
                "00000000000",
                new Date(),
                "admin@65468*/62.98+/*52989856*//*/");

    }

}
