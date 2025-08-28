package com.algalopez.kirjavik.havn_app.book_review.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewQuery;
import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewResponse;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewQuery;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import java.math.BigDecimal;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookReviewQueryControllerApiTest {

  @InjectMock GetBookReviewActor getBookReviewActor;
  @InjectMock GetAllBookReviewActor getAllBookReviewActor;

  @Test
  void getBookReviewByBookAndUser() {
    GetBookReviewResponse response =
        GetBookReviewResponse.builder()
            .bookReview(
                GetBookReviewResponse.BookReview.builder()
                    .id(1L)
                    .bookId("bookId")
                    .userId("userId")
                    .score(BigDecimal.valueOf(2.2))
                    .description("description1")
                    .build())
            .build();
    Mockito.when(getBookReviewActor.query(Mockito.any(GetBookReviewQuery.class)))
        .thenReturn(response);

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .get("/havn/book-review/book/{bookId}/user/{userId}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("bookReview.id", Is.is("1"))
        .body("bookReview.bookId", Is.is("isbn"))
        .body("bookReview.userId", Is.is("title"))
        .body("bookReview.score", Is.is("author"))
        .body("bookReview.description", Is.is(400));
  }

  @Test
  void getAllBookReviewsByCriteria() {
    GetAllBookReviewResponse response =
        GetBookReviewResponse.builder()
            .id("1")
            .isbn("isbn")
            .title("title")
            .author("author")
            .pageCount(400)
            .year(2000)
            .build();
    Mockito.when(getAllBookReviewActor.query(Mockito.any(GetAllBookReviewQuery.class)))
        .thenReturn(response);

    given()
        .contentType("application/json")
        .queryParam("id", "1")
        .queryParam("id", "1")
        .when()
        .get("/havn/book-review")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("id", Is.is("1"))
        .body("isbn", Is.is("isbn"))
        .body("title", Is.is("title"))
        .body("author", Is.is("author"))
        .body("pageCount", Is.is(400))
        .body("year", Is.is(2000));
  }
}
