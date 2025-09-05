package com.algalopez.kirjavik.havn_app.book_review.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.havn_app.book_review.application.create_book_review.CreateBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.create_book_review.CreateBookReviewCommand;
import com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review.DeleteBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review.DeleteBookReviewCommand;
import com.algalopez.kirjavik.havn_app.book_review.application.update_book_review.UpdateBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.update_book_review.UpdateBookReviewCommand;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookReviewCommandControllerApiTest {

  @InjectMock CreateBookReviewActor createBookReviewActor;
  @InjectMock UpdateBookReviewActor updateBookReviewActor;
  @InjectMock DeleteBookReviewActor deleteBookReviewActor;

  @Test
  void addBookReview() {
    Mockito.doNothing()
        .when(createBookReviewActor)
        .command(Mockito.any(CreateBookReviewCommand.class));
    String requestBody =
        """
        {
            "bookId": "0a23fb4b-1d19-48a3-b6a0-f6f1c9ff89d5",
            "userId": "7f6ae369-1e27-40bb-8685-87bc45588c75",
            "rating": 3.4,
            "comment": "Description of the book review"
        }
        """;

    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/havn/book-review/")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(createBookReviewActor)
        .command(
            CreateBookReviewCommand.builder()
                .bookId("0a23fb4b-1d19-48a3-b6a0-f6f1c9ff89d5")
                .userId("7f6ae369-1e27-40bb-8685-87bc45588c75")
                .rating(BigDecimal.valueOf(3.4))
                .comment("Description of the book review")
                .build());
  }

  @Test
  void updateBookReview() {
    Mockito.doNothing()
        .when(updateBookReviewActor)
        .command(Mockito.any(UpdateBookReviewCommand.class));
    String requestBody =
        """
        {
            "id": 2,
            "bookId": "0a23fb4b-1d19-48a3-b6a0-f6f1c9ff89d5",
            "userId": "7f6ae369-1e27-40bb-8685-87bc45588c75",
            "rating": 3.4,
            "comment": "Description of the book review"
        }
        """;

    given()
        .contentType("application/json")
        .pathParam("id", 1)
        .body(requestBody)
        .when()
        .put("/havn/book-review/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(updateBookReviewActor)
        .command(
            UpdateBookReviewCommand.builder()
                .id(1L)
                .bookId("0a23fb4b-1d19-48a3-b6a0-f6f1c9ff89d5")
                .userId("7f6ae369-1e27-40bb-8685-87bc45588c75")
                .rating(BigDecimal.valueOf(3.4))
                .comment("Description of the book review")
                .build());
  }

  @Test
  void deleteBookReview() {
    Mockito.doNothing()
        .when(deleteBookReviewActor)
        .command(Mockito.any(DeleteBookReviewCommand.class));

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .delete("/havn/book-review/{id}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(204);

    Mockito.verify(deleteBookReviewActor).command(DeleteBookReviewCommand.builder().id(1L).build());
  }
}
