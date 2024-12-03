package br.com.courses.service;

import br.com.courses.handler.requesthandler.security.jwt.JWTContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class WebService {

    private final RestTemplate restTemplate;

    @Value("${api-course.base-url}")
    String baseUrl;

    public <T> ResponseEntity<T> get(String path, Class<T> responseType) {

        String token = extractTokenFromSecurityContext();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange((baseUrl + path), HttpMethod.GET, entity, responseType);

    }

    public <T> ResponseEntity post(String path, Object requestBody, Class<T> responseType) {

        String token = extractTokenFromSecurityContext();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        try {

            ResponseEntity<T> responseEntity = restTemplate.exchange(
                    baseUrl + path, HttpMethod.POST, entity, responseType);

            return responseEntity;

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());

        } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal Server Error"));
        }
    }

    private String extractTokenFromSecurityContext() {
        return JWTContext.getJwt();
    }

    private String createErrorResponse(String errorMessage) {
        return "{\"error\": \"" + errorMessage + "\"}";
    }

}
