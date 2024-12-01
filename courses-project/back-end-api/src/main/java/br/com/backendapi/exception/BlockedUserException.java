package br.com.backendapi.exception;

public class BlockedUserException extends RuntimeException {

    public BlockedUserException() {
        super("Your account is temporarily locked due to multiple failed login attempts. Please try again later.");
    }

}
