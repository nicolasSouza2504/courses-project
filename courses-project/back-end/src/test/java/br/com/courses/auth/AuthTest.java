package br.com.courses.auth;


import br.com.courses.domain.user.UserLogin;
import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.service.user.IUserService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTest {

    private static IUserService userService;

    @BeforeAll
    public static void setup() {

        userService = mock(IUserService.class);

        RestAssured.baseURI = "http://localhost:8080/api/v1";

    }

    @Test
    void testLoginByEmailSuccessful() {

        Integer userId = registerUser("johndoe@example.com", "913.625.010-42", "P@ssw0rd123");

        given()
                .contentType("application/json")
                .body(new UserLogin("johndoe@example.com", null, "P@ssw0rd123"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("message", equalTo("Login Successful"))
                .body("data.id", notNullValue())
                .body("data.token", notNullValue());

        deleteUser(userId);

    }

    @Test
    void testLoginByCpfSuccessful() {

        Integer userId = registerUser("johndoe@example.com", "913.625.010-42", "P@ssw0rd123");

        given()
                .contentType("application/json")
                .body(new UserLogin(null, "913.625.010-42", "P@ssw0rd123"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("message", equalTo("Login Successful"))
                .body("data.id", notNullValue())
                .body("data.token", notNullValue());

        deleteUser(userId);

    }

    @Test
    void testLoginBadCredentials() {

        Integer userId = registerUser("johndoe@example.com", "913.625.010-42", "P@ssw0rd123");


        given()
                .contentType("application/json")
                .body(new UserLogin("johndoe@example.com", null, "WrongPassword"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .body("message", equalTo("Bad credentials"));

        deleteUser(userId);

    }

    @Test
    void testLoginBlockedAfterFiveFailedAttempts() {

        Integer userId = registerUser("johndoe@example.com", "913.625.010-42", "P@ssw0rd123");

        for (int i = 0; i < 5; i++) {

            given()
                    .contentType("application/json")
                    .body(new UserLogin("johndoe@example.com", null, "WrongPassword"))
                    .when()
                    .post("/auth/login")
                    .then()
                    .statusCode(401)
                    .body("message", equalTo("Bad credentials"));

        }

        given()
                .contentType("application/json")
                .body(new UserLogin("johndoe@example.com", null, "P@ssw0rd123"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(403)
                .body("message", equalTo("Your account is temporarily locked due to multiple failed login attempts. Please try again later."));

        deleteUser(userId);

    }

    private Integer registerUser(String email, String cpf, String password) {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", email, cpf, new java.util.Date(), password);

        return given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .body("email", equalTo(email))
                .body("nome", equalTo("John Doe"))
                .body("id", notNullValue())
                .extract().path("id");

    }

    private void deleteUser(Integer userId) {

        given()
                .when()
                .delete("/user/" + userId)
                .then()
                .statusCode(204);

    }

}
