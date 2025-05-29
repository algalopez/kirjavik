package com.algalopez.kirjavik.backoffice_app.user.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserQuery;
import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class UserQueryControllerApiTest {

  @InjectMock GetUserActor getUserActor;

  @Test
  void getUser() {
    GetUserResponse response =
        GetUserResponse.builder().id("1").name("name").email("email").build();
    Mockito.when(getUserActor.query(Mockito.any(GetUserQuery.class))).thenReturn(response);

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .get("/backoffice/user/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("id", Is.is("1"))
        .body("name", Is.is("name"))
        .body("email", Is.is("email"));
  }
}
