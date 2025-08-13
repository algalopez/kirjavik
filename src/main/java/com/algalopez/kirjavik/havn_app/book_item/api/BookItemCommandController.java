package com.algalopez.kirjavik.havn_app.book_item.api;

import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.add_book_item.AddBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item.BorrowBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item.BorrowBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item.RemoveBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item.RemoveBookItemCommand;
import com.algalopez.kirjavik.havn_app.book_item.application.return_book_item.ReturnBookItemActor;
import com.algalopez.kirjavik.havn_app.book_item.application.return_book_item.ReturnBookItemCommand;
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
  private final BorrowBookItemActor borrowBookItemActor;
  private final RemoveBookItemActor removeBookItemActor;
  private final ReturnBookItemActor returnBookItemActor;

  @Operation(summary = "Add book item")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/add")
  public void addBookItem(AddBookItemCommand command) {
    addBookItemActor.command(command);
  }

  @Operation(summary = "Borrow book item")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/borrow")
  public void borrowBookItem(BorrowBookItemCommand command) {
    borrowBookItemActor.command(command);
  }

  @Operation(summary = "Return book item")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/return")
  public void returnBookItem(ReturnBookItemCommand command) {
    returnBookItemActor.command(command);
  }

  @Operation(summary = "Remove book item")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/remove")
  public void removeBookItem(RemoveBookItemCommand command) {
    removeBookItemActor.command(command);
  }
}
