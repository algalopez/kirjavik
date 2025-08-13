package com.algalopez.kirjavik.havn_app.book_item.api;

import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item.BorrowBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item.BorrowBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item.RemoveBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item.RemoveBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.return_book_item.ReturnBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.return_book_item.ReturnBookItemCommand;
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
  @InjectMock BorrowBookItemActor borrowBookItemActor;
  @InjectMock ReturnBookItemActor returnBookItemActor;
  @InjectMock RemoveBookItemActor removeBookItemActor;

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
        .post("/havn/book-item/add")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(addBookItemActor)
        .command(AddBookItemCommand.builder().id("1").bookId("2").userId("3").build());
  }

  @Test
  void borrowBookItem() {
    Mockito.doNothing().when(borrowBookItemActor).command(Mockito.any(BorrowBookItemCommand.class));
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
        .post("/havn/book-item/borrow")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(borrowBookItemActor)
        .command(BorrowBookItemCommand.builder().id("1").bookId("2").userId("3").build());
  }

  @Test
  void returnBookItem() {
    Mockito.doNothing().when(returnBookItemActor).command(Mockito.any(ReturnBookItemCommand.class));
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
        .post("/havn/book-item/return")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(returnBookItemActor)
        .command(ReturnBookItemCommand.builder().id("1").bookId("2").userId("3").build());
  }

  @Test
  void removeBookItem() {
    Mockito.doNothing().when(removeBookItemActor).command(Mockito.any(RemoveBookItemCommand.class));
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
        .post("/havn/book-item/remove")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(removeBookItemActor)
        .command(RemoveBookItemCommand.builder().id("1").bookId("2").userId("3").build());
  }
}
