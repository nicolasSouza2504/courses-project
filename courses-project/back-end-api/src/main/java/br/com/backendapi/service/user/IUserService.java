package br.com.courses.service.user;

import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.domain.user.UserResponseData;

public interface IUserService {
    UserResponseData create(UserRegisterDTO userRegister);
    void delete(Long id);
}
