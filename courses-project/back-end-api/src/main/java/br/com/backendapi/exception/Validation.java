package br.com.backendapi.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Validation extends RuntimeException {

    public Map<String, String> validations = new HashMap<>();

    public Validation() {
        super("Validations Occurred");
    }

    public Validation add(String field, String message) {

        validations.put(field, message);

        return this;

    }

    public void throwIfHasErrors() {

        if (!validations.isEmpty()) {
            throw this;
        }

    }

}

