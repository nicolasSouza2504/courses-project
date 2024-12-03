package br.com.courses.resource.auth;

import br.com.courses.domain.user.UserLogin;
import br.com.courses.exception.BlockedUserException;
import br.com.courses.handler.requesthandler.security.jwt.JWTContext;
import br.com.courses.handler.requesthandler.security.jwt.JwtUtils;
import br.com.courses.handler.requesthandler.security.user.AuthyUserDetails;
import br.com.courses.repository.IUserRepository;
import br.com.courses.response.ApiResponse;
import br.com.courses.response.JwtResponse;
import br.com.courses.service.login.LoginAttemptService;
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

            JWTContext.setJwt(jwt);

            AuthyUserDetails userDetails = (AuthyUserDetails) authentication.getPrincipal();

            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt, userDetails.getCpf());

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
