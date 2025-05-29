package com.algalopez.kirjavik.backoffice_app.user.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.backoffice_app.user.application.create_user.CreateUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.create_user.CreateUserCommand;
import com.algalopez.kirjavik.backoffice_app.user.application.delete_user.DeleteUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.delete_user.DeleteUserCommand;
import com.algalopez.kirjavik.backoffice_app.user.application.update_user.UpdateUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.update_user.UpdateUserCommand;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class UserCommandControllerApiTest {

  @InjectMock CreateUserActor createUserActor;
  @InjectMock UpdateUserActor updateUserActor;
  @InjectMock DeleteUserActor deleteUserActor;

  @Test
  void createUser() {
    Mockito.doNothing().when(createUserActor).command(Mockito.any(CreateUserCommand.class));
    String requestBody =
        """
        {
            "id": "1",
            "name": "John Doe",
            "email": "0zv8t@example.com"
        }
        """;

    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/backoffice/user")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(createUserActor)
        .command(
            CreateUserCommand.builder()
                .id("1")
                .name("John Doe")
                .email("0zv8t@example.com")
                .build());
  }

  @Test
  void updateUser() {
    Mockito.doNothing().when(updateUserActor).command(Mockito.any(UpdateUserCommand.class));
    String requestBody =
        """
        {
            "id": "999",
            "name": "John Doe",
            "email": "0zv8t@example.com"
        }
        """;

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .body(requestBody)
        .when()
        .put("/backoffice/user/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(updateUserActor)
        .command(
            UpdateUserCommand.builder()
                .id("1")
                .name("John Doe")
                .email("0zv8t@example.com")
                .build());
  }

  @Test
  void deleteUser() {
    Mockito.doNothing().when(deleteUserActor).command(Mockito.any(DeleteUserCommand.class));

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .delete("/backoffice/user/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(deleteUserActor).command(DeleteUserCommand.builder().id("1").build());
  }
}
