package com.algalopez.kirjavik.backoffice_app.book.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.backoffice_app.book.application.create_book.CreateBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.create_book.CreateBookCommand;
import com.algalopez.kirjavik.backoffice_app.book.application.delete_book.DeleteBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.delete_book.DeleteBookCommand;
import com.algalopez.kirjavik.backoffice_app.book.application.update_book.UpdateBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.update_book.UpdateBookCommand;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookCommandControllerApiTest {

  @InjectMock CreateBookActor createBookActor;
  @InjectMock UpdateBookActor updateBookActor;
  @InjectMock DeleteBookActor deleteBookActor;

  @Test
  void createBook() {
    Mockito.doNothing().when(createBookActor).command(Mockito.any(CreateBookCommand.class));
    String requestBody =
        """
        {
            "id": "1",
            "isbn": "1234567890",
            "title": "Test Book",
            "author": "John Doe",
            "pageCount": 100,
            "year": 2022
        }
        """;

    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/backoffice/book")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(createBookActor)
        .command(
            CreateBookCommand.builder()
                .id("1")
                .isbn("1234567890")
                .title("Test Book")
                .author("John Doe")
                .pageCount(100)
                .year(2022)
                .build());
  }

  @Test
  void updateBook() {
    Mockito.doNothing().when(updateBookActor).command(Mockito.any(UpdateBookCommand.class));
    String requestBody =
        """
        {
            "id": "999",
            "isbn": "1234567890",
            "title": "Test Book",
            "author": "John Doe",
            "pageCount": 100,
            "year": 2022
        }
        """;

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .body(requestBody)
        .when()
        .put("/backoffice/book/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(updateBookActor)
        .command(
            UpdateBookCommand.builder()
                .id("1")
                .isbn("1234567890")
                .title("Test Book")
                .author("John Doe")
                .pageCount(100)
                .year(2022)
                .build());
  }

  @Test
  void deleteBook() {
    Mockito.doNothing().when(deleteBookActor).command(Mockito.any(DeleteBookCommand.class));

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .delete("/backoffice/book/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(deleteBookActor).command(DeleteBookCommand.builder().id("1").build());
  }
}
