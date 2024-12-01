package br.com.backendapi.domain.user;

public record UserResponseData(Long id, String email, String nome) {

    public UserResponseData(User user ) {
        this(user.getId(), user.getEmail(), user.getName());
    }

}
