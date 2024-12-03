package br.com.backendapi.resource.auth;

import br.com.backendapi.domain.user.UserLogin;
import br.com.backendapi.exception.BlockedUserException;
import br.com.backendapi.handler.requesthandler.security.jwt.JwtUtils;
import br.com.backendapi.handler.requesthandler.security.user.AuthyUserDetails;
import br.com.backendapi.repository.IUserRepository;
import br.com.backendapi.response.ApiResponse;
import br.com.backendapi.response.JwtResponse;
import br.com.backendapi.service.login.LoginAttemptService;
import io.micrometer.common.util.StringUtils;
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

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IUserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLogin request) {

        String email = null;

        try {

            email = StringUtils.isNotEmpty(request.email()) ? request.email() : getEmail(request.cpf());

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            email, request.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateTokenForUser(authentication);

            AuthyUserDetails userDetails = (AuthyUserDetails) authentication.getPrincipal();

            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));

        } catch (AuthenticationException e) {

            try {

                loginAttemptService.incrementFailedAttempts(userRepository.findByEmail(email));

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));

            } catch (BlockedUserException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(ex.getMessage(), null));
            }

        }

    }

    private String getEmail(String cpf) {

        if (StringUtils.isNotEmpty(cpf)) {

            cpf = cpf.replaceAll("[^0-9]", "");

            return Optional.ofNullable(userRepository.findByCpf(cpf)).map(user -> user.getEmail()).orElse(null);

        } else {
            return null;
        }

    }

}
