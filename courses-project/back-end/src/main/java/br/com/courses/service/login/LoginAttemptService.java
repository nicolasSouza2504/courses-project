package br.com.courses.service.login;

import br.com.courses.domain.user.User;
import br.com.courses.exception.BlockedUserException;
import br.com.courses.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final IUserRepository userRepository;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION = 5 * 60 * 1000;

    public boolean isBlocked(User user) {

        Integer failedAttempts = user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0;

        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {

            if (user.getLockoutTime() == null || user.getLockoutTime().isBefore(LocalDateTime.now())) {

                resetFailedAttempts(user);

                return false;

            }

            return true;

        }

        return false;

    }

    public void incrementFailedAttempts(User user) {

        Optional.ofNullable(user)
                .ifPresent((userAttempt) -> {

                    if (!isBlocked(userAttempt)) {

                        Integer attempts = userAttempt.getFailedLoginAttempts() != null ? userAttempt.getFailedLoginAttempts() : 0;

                        userAttempt.setFailedLoginAttempts(attempts + 1);

                        if (userAttempt.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
                            userAttempt.setLockoutTime(LocalDateTime.now().plusMinutes(5));
                        }

                        userRepository.save(userAttempt);

                    } else {
                        throw new BlockedUserException();
                    }

                });

    }

    public void resetFailedAttempts(User user) {

        user.setFailedLoginAttempts(0);
        user.setLockoutTime(null);

        userRepository.save(user);

    }

}
