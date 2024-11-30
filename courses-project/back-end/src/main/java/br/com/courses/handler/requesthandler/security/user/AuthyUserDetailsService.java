package br.com.courses.handler.requesthandler.security.user;

import br.com.courses.domain.user.User;
import br.com.courses.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthyUserDetailsService implements UserDetailsService {

    private final IUserRepository IUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = Optional.ofNullable(IUserRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return AuthyUserDetails.buildUserDetails(user);

    }


}
