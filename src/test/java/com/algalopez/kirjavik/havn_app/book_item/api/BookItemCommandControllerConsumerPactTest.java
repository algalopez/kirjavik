package com.algalopez.kirjavik.havn_app.book_item.api;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "kirjavik-havn-provider", pactVersion = PactSpecVersion.V3)
@PactDirectory("pact")
@EnabledIfSystemProperty(
    named = "pact.consumer.enabled",
    matches = "true",
    disabledReason = "pact.consumer.enabled system property not set to true")
public class BookItemCommandControllerConsumerPactTest {

  @Pact(provider = "kirjavik-havn-provider", consumer = "kirjavik-havn-consumer")
  RequestResponsePact createPact(PactDslWithProvider builder) {

    return builder
        .given("scenario_add_book_item")
        .uponReceiving("A request to add a book item")
        .path("/havn/book-item/")
        .method("POST")
        .body(
            """
            {
                "id": "1",
                "bookId": "2",
                "userId": "3"
            }
            """)
        .willRespondWith()
        .status(204)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "createPact")
  void testGetMatchState(MockServer mockServer) {
    RestAssured.baseURI = mockServer.getUrl();

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(
            """
               {
                  "id": "1",
                  "bookId": "2",
                  "userId": "3"
               }
               """)
        .when()
        .post("/havn/book-item/")
        .then()
        .statusCode(204);
  }
}
