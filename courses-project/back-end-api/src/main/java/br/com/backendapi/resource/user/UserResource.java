package br.com.courses.resource.user;

import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.domain.user.UserResponseData;
import br.com.courses.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserResource {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponseData> register(@RequestBody UserRegisterDTO userRegisterDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(userRegisterDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.noContent().build();

    }

}
