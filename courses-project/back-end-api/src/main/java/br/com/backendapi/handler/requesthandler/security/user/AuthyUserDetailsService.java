package br.com.courses.handler.requesthandler.security.user;

import br.com.courses.domain.user.User;
import br.com.courses.exception.BlockedUserException;
import br.com.courses.repository.IUserRepository;
import br.com.courses.service.login.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthyUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!loginAttemptService.isBlocked(user)) {
            return AuthyUserDetails.buildUserDetails(user);
        } else {
            throw new BlockedUserException();
        }

    }


}