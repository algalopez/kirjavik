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
import java.util.List;
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
            .id(1L)
            .bookId("bookId")
            .userId("userId")
            .rating(BigDecimal.valueOf(2.2))
            .comment("comment")
            .build();
    Mockito.when(getBookReviewActor.query(Mockito.any(GetBookReviewQuery.class)))
        .thenReturn(response);

    given()
        .contentType("application/json")
        .pathParam("bookId", "1")
        .pathParam("userId", "2")
        .when()
        .get("/havn/book-review/book/{bookId}/user/{userId}")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("id", Is.is(1))
        .body("bookId", Is.is("bookId"))
        .body("userId", Is.is("userId"))
        .body("rating.toString()", Is.is("2.2"))
        .body("comment", Is.is("comment"));

    Mockito.verify(getBookReviewActor)
        .query(GetBookReviewQuery.builder().bookId("1").userId("2").build());
  }

  @Test
  void getAllBookReviewsByCriteria() {
    GetAllBookReviewResponse response =
        GetAllBookReviewResponse.builder()
            .bookReviews(
                List.of(
                    GetAllBookReviewResponse.BookReview.builder()
                        .id(1L)
                        .bookId("bookId")
                        .userId("userId")
                        .rating(BigDecimal.valueOf(2.2))
                        .comment("comment")
                        .build()))
            .build();
    Mockito.when(getAllBookReviewActor.query(Mockito.any(GetAllBookReviewQuery.class)))
        .thenReturn(response);

    given()
        .contentType("application/json")
        .queryParam("bookId", "1")
        .queryParam("userId", "2")
        .when()
        .get("/havn/book-review")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("bookReviews[0].id", Is.is(1))
        .body("bookReviews[0].bookId", Is.is("bookId"))
        .body("bookReviews[0].userId", Is.is("userId"))
        .body("bookReviews[0].rating.toString()", Is.is("2.2"))
        .body("bookReviews[0].comment", Is.is("comment"));

    Mockito.verify(getAllBookReviewActor)
        .query(GetAllBookReviewQuery.builder().bookId("1").userId("2").build());
  }
}
