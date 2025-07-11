package com.algalopez.kirjavik.havn_app.book_item.api;

import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemCommand;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Haven-BookItem")
@RequiredArgsConstructor
@Path("/havn/book-item/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookItemCommandController {

  private final AddBookItemActor addBookItemActor;

  @Operation(summary = "Add book item")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/")
  public void addBookItem(AddBookItemCommand command) {
    addBookItemActor.command(command);
  }
}
