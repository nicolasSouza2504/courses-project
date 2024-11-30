package br.com.courses.service.user;

import br.com.courses.domain.user.User;
import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.domain.user.UserResponseData;
import br.com.courses.exception.UserAlreadyExistsException;
import br.com.courses.exception.Validation;
import br.com.courses.repository.IUserRepository;
import br.com.courses.util.CPFCNPJValidator;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

//R2.7 - Integração da Pessoa
//	R2.7.1 - Ao salvar, a pessoa deve ser enviada para a fila de integração com a API (RabbitMQ)
//R2.7.2 - O envio para a fila de integração deve ocorrer de forma assíncrona para não impactar a performance da aplicação.
//R2.7.3 - Deve haver um mecanismo de retry para tentativas falhas de envio à fila, com um número máximo de tentativas configurável.
//	R2.7.4 - O sistema deve validar se a pessoa já existe na API antes de enviar a mensagem à fila, para evitar duplicações.

//R2.8 - Criar testes unitários
//R2.6.2 - Após salvar os dados com sucesso, o sistema deve autenticar automaticamente o usuário e redirecioná-lo para a tela de inscrição de cursos.

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseData create(UserRegisterDTO userRegister) {

        return Optional.of(userRegister)
                .filter(user -> !userRepository.existsByCpf(userRegister.cpf()))
                .map(req -> {

                    validateFields(userRegister);

                    User user = buildUser(userRegister);

                    userRepository.save(user);

                    return new UserResponseData(user);

                })
                .orElseThrow(() -> new UserAlreadyExistsException("Usuário já cadastrado"));

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void validateFields(UserRegisterDTO userRegister) {

        Validation validation = new Validation();

        if (!validName(userRegister.name())) {
            validation.add("Name", "Nome deve ser preenchido, conter nome e sobrenome apenas letras e iniciais maiúsculas");
        }

        if (userRegister.birthDate() != null && userRegister.birthDate().after(new Date())) {
            validation.add("BirthDate", "Data de Nascimento não pode ser posterior a data atual");
        }

        if (StringUtils.isEmpty(userRegister.cpf()) || !CPFCNPJValidator.isValidCpf(userRegister.cpf())) {
            validation.add("CPF", "Informe um CPF válido");
        }

        if (StringUtils.isNotEmpty(userRegister.email()) && !userRegister.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            validation.add("Email", "Informe um email válido");
        }

        if (!validPassword(userRegister.password())) {
            validation.add("Password", "Informe uma senha válida");
        }
        
        validation.throwIfHasErrors();

    }

    private Boolean validPassword(String password) {
        return StringUtils.isNotEmpty(password)
                && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    private Boolean validName(String name) {
        return StringUtils.isNotEmpty(name) && name.matches("^[A-Z][a-z]+ [A-Z][a-z]+$");
    }

    private User buildUser(UserRegisterDTO userRegister) {

        User user = new User();

        user.setName(userRegister.name());
        user.setEmail(userRegister.email());
        user.setCpf(userRegister.cpf().replaceAll("[^0-9]", ""));
        user.setBirthDate(DateUtils.truncate(userRegister.birthDate(), 5));
        user.setPassword(passwordEncoder.encode(userRegister.password()));

        return user;

    }

}
