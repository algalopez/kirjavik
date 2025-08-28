package com.algalopez.kirjavik.havn_app.book_review.api;

import com.algalopez.kirjavik.havn_app.book_review.application.create_book_review.CreateBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.create_book_review.CreateBookReviewCommand;
import com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review.DeleteBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review.DeleteBookReviewCommand;
import com.algalopez.kirjavik.havn_app.book_review.application.update_book_review.UpdateBookReviewActor;
import com.algalopez.kirjavik.havn_app.book_review.application.update_book_review.UpdateBookReviewCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Havn-BookReview")
@RequiredArgsConstructor
@Path("/havn/book-review/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookReviewCommandController {

  private final CreateBookReviewActor createBookReviewActor;
  private final UpdateBookReviewActor updateBookReviewActor;
  private final DeleteBookReviewActor deleteBookReviewActor;

  @Operation(summary = "Add book review")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/")
  public void addBookReview(CreateBookReviewCommand command) {
    createBookReviewActor.command(command);
  }

  @Operation(summary = "Update book review")
  @APIResponse(responseCode = "200")
  @PUT
  @Path("/{id}")
  public void updateBookReview(@PathParam("id") Long id, UpdateBookReviewCommand command) {
    UpdateBookReviewCommand commandWithId = command.toBuilder().id(id).build();
    updateBookReviewActor.command(commandWithId);
  }

  @Operation(summary = "Add book review")
  @APIResponse(responseCode = "204")
  @DELETE
  @Path("/{id}")
  public void deleteBookReview(@PathParam("id") Long id) {
    DeleteBookReviewCommand command = DeleteBookReviewCommand.builder().id(id).build();
    deleteBookReviewActor.command(command);
  }
}
