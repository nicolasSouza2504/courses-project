package br.com.courses.users;

import br.com.courses.domain.user.UserRegisterDTO;
import br.com.courses.service.user.IUserService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserResourceTest {

    private static IUserService userService;

    @BeforeAll
    public static void setup() {

        userService = mock(IUserService.class);

        RestAssured.baseURI = "http://localhost:8080/api/v1";

    }

    @Test
    void testRegisterUserSuccessfully() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", "johndoe@example.com", "913.625.010-42", new java.util.Date(), "P@ssw0rd123");

        Integer id = given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .body("email", equalTo("johndoe@example.com"))
                .body("nome", equalTo("John Doe"))
                .body("id", notNullValue())
                .extract().path("id");

        given()
                .when()
                .delete("/user/" + id)
                .then()
                .statusCode(204);

    }

    @Test
    void testRegisterUserWithInvalidCpf() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", "johndoe@example.com", "invalid_cpf", null, "P@ssw0rd123");

        given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(400)
                .body("CPF", equalTo("Informe um CPF válido"));

    }

    @Test
    void testRegisterUserWithInvalidEmail() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", "invalid-email", "913.625.010-42", new java.util.Date(), "P@ssw0rd123");

        given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(400)  // Bad Request due to invalid email
                .body("Email", equalTo("Informe um email válido"));
    }

    @Test
    void testRegisterUserWithInvalidBirthDate() {

        Calendar futureDate = Calendar.getInstance();

        futureDate.add(Calendar.YEAR, 1); // Set a date one year in the future

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", "johndoe@example.com", "913.625.010-42", futureDate.getTime(), "P@ssw0rd123");

        given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(400)  // Bad Request due to invalid birthdate
                .body("BirthDate", equalTo("Data de Nascimento não pode ser posterior a data atual"));

    }

    @Test
    void testRegisterUserWithInvalidPassword() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("John Doe", "johndoe@example.com", "913.625.010-42", new java.util.Date(), "short");

        given()
                .contentType("application/json")
                .body(userRegisterDTO)
                .when()
                .post("/user")
                .then()
                .statusCode(400)  // Bad Request due to invalid password
                .body("Password", equalTo("Informe uma senha válida"));

    }

}
