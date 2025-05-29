package com.algalopez.kirjavik.backoffice_app.book.api;

import static io.restassured.RestAssured.given;

import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksActor;
import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksQuery;
import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksResponse;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookQuery;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookQueryControllerApiTest {

  @InjectMock GetBookActor getBookActor;
  @InjectMock GetAllBooksActor getAllBooksActor;

  @Test
  void getBook() {
    GetBookResponse response =
        GetBookResponse.builder()
            .id("1")
            .isbn("isbn")
            .title("title")
            .author("author")
            .pageCount(400)
            .year(2000)
            .build();
    Mockito.when(getBookActor.query(Mockito.any(GetBookQuery.class))).thenReturn(response);

    given()
        .contentType("application/json")
        .pathParam("id", "1")
        .when()
        .get("/backoffice/book/{id}")
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

  @Test
  void getAllBooks() {
    GetAllBooksResponse response =
        GetAllBooksResponse.builder()
            .books(
                List.of(
                    GetAllBooksResponse.BookDto.builder()
                        .id("1")
                        .isbn("isbn")
                        .title("title")
                        .author("author")
                        .pageCount(400)
                        .year(2000)
                        .build()))
            .build();
    Mockito.when(getAllBooksActor.query(Mockito.any(GetAllBooksQuery.class))).thenReturn(response);

    given()
        .contentType("application/json")
        .queryParam("field", "year")
        .queryParam("operator", "GREATER_THAN")
        .queryParam("value", "2000")
        .when()
        .get("/backoffice/book/")
        .then()
        .log()
        .ifValidationFails(LogDetail.BODY)
        .statusCode(200)
        .body("books.size()", Is.is(1))
        .body("books[0].id", Is.is("1"))
        .body("books[0].isbn", Is.is("isbn"))
        .body("books[0].title", Is.is("title"))
        .body("books[0].author", Is.is("author"))
        .body("books[0].pageCount", Is.is(400))
        .body("books[0].year", Is.is(2000));
  }
}
