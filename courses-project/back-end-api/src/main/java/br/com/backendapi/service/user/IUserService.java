package br.com.backendapi.service.user;

import br.com.backendapi.domain.user.UserRegisterDTO;
import br.com.backendapi.domain.user.UserResponseData;

public interface IUserService {
    UserResponseData create(UserRegisterDTO userRegister);
    void delete(Long id);
}
