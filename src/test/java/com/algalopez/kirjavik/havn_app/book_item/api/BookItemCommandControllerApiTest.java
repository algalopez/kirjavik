package com.algalopez.kirjavik.havn_app.book_item.api;

import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemCommand;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookItemCommandControllerApiTest {
  @InjectMock AddBookItemActor addBookItemActor;

  @Test
  void addBookItem() {
    Mockito.doNothing().when(addBookItemActor).command(Mockito.any(AddBookItemCommand.class));
    String requestBody =
        """
        {
            "id": "1",
            "bookId": "2",
            "userId": "3"
        }
        """;

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(requestBody)
        .when()
        .post("/havn/book-item/")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(addBookItemActor)
        .command(AddBookItemCommand.builder().id("1").bookId("2").userId("3").build());
  }
}
