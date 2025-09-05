package com.algalopez.kirjavik.havn_app.book_review.api;

import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewQuery;
import com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review.GetAllBookReviewResponse;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewQuery;
import com.algalopez.kirjavik.havn_app.book_review.application.get_book_review.GetBookReviewResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Havn-BookReview")
@RequiredArgsConstructor
@Path("/havn/book-review/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookReviewQueryController {

  private final GetBookReviewActor getBookReviewActor;
  private final GetAllBookReviewActor getAllBookReviewActor;

  @Operation(summary = "Get book review by bookId and userId")
  @APIResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = GetBookReviewResponse.class)))
  @GET
  @Path("/book/{bookId}/user/{userId}")
  public GetBookReviewResponse getBookReviewByBookAndUser(
      @PathParam("bookId") String bookId, @PathParam("userId") String userId) {
    GetBookReviewQuery query = GetBookReviewQuery.builder().bookId(bookId).userId(userId).build();
    return getBookReviewActor.query(query);
  }

  @Operation(summary = "Get all book reviews by criteria")
  @APIResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = GetBookReviewResponse.class)))
  @GET
  @Path("/")
  public GetAllBookReviewResponse getAllBookReviewsByCriteria(
      @QueryParam("bookId") String bookId, @QueryParam("userId") String userId) {
    GetAllBookReviewQuery query =
        GetAllBookReviewQuery.builder().bookId(bookId).userId(userId).build();
    return getAllBookReviewActor.query(query);
  }
}
