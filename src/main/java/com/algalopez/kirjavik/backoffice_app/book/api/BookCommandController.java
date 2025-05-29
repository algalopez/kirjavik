package com.algalopez.kirjavik.backoffice_app.book.api;

import com.algalopez.kirjavik.backoffice_app.book.application.create_book.CreateBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.create_book.CreateBookCommand;
import com.algalopez.kirjavik.backoffice_app.book.application.delete_book.DeleteBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.delete_book.DeleteBookCommand;
import com.algalopez.kirjavik.backoffice_app.book.application.update_book.UpdateBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.update_book.UpdateBookCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Backoffice-Book")
@RequiredArgsConstructor
@Path("/backoffice/book/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookCommandController {

  private final CreateBookActor createBookActor;
  private final UpdateBookActor updateBookActor;
  private final DeleteBookActor deleteBookActor;

  @Operation(summary = "Create book")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/")
  public void createBook(CreateBookCommand command) {
    createBookActor.command(command);
  }

  @Operation(summary = "Update book")
  @APIResponse(responseCode = "204")
  @PUT
  @Path("/{id}")
  public void updateBook(@PathParam("id") String id, UpdateBookCommand command) {
    UpdateBookCommand commandWithId = command.toBuilder().id(id).build();
    updateBookActor.command(commandWithId);
  }

  @Operation(summary = "Delete book by id")
  @APIResponse(responseCode = "204")
  @DELETE
  @Path("/{id}")
  public void deleteBook(@PathParam("id") String id) {
    DeleteBookCommand command = DeleteBookCommand.builder().id(id).build();
    deleteBookActor.command(command);
  }
}
