package br.com.courses.resource.auth;

import br.com.courses.domain.user.UserLogin;
import br.com.courses.handler.requesthandler.security.jwt.JwtUtils;
import br.com.courses.handler.requesthandler.security.user.AuthyUserDetails;
import br.com.courses.response.ApiResponse;
import br.com.courses.response.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthResource {

    //R1 - Tela de Login
//R1.1 - A tela deve permitir que o usuário faça login utilizando CPF ou e-mail e uma senha.
//R1.2 - Se as credenciais estiverem incorretas, o sistema deve exibir uma mensagem de erro indicando que CPF / E-mail ou senha estão incorretos.
//R1.3 - O sistema deve implementar medidas de segurança, como limitar tentativas de login consecutivas e aplicar bloqueio temporário após várias tentativas falhas.
//R1.4 - Ao clicar no botão cadastre-se, deve ser redirecionado para a tela de cadastro de pessoa
//R1.5 - Criar testes unitários para verificar o comportamento da tela de login.

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLogin request) {

        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.email(), request.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateTokenForUser(authentication);

            AuthyUserDetails userDetails = (AuthyUserDetails) authentication.getPrincipal();

            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
