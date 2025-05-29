package com.algalopez.kirjavik.backoffice_app.book.api;

import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksActor;
import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksQuery;
import com.algalopez.kirjavik.backoffice_app.book.application.get_all_books.GetAllBooksResponse;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookActor;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookQuery;
import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Backoffice-Book")
@RequiredArgsConstructor
@Path("/backoffice/book/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookQueryController {

  private final GetBookActor getBookActor;
  private final GetAllBooksActor getAllBooksActor;

  @Operation(summary = "Get book by id")
  @APIResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = GetBookResponse.class)))
  @GET
  @Path("/{id}")
  public GetBookResponse getBook(@PathParam("id") String id) {
    GetBookQuery query = GetBookQuery.builder().id(id).build();
    return getBookActor.query(query);
  }

  @Operation(summary = "Get all books by filter")
  @APIResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = GetBookResponse.class)))
  @GET
  @Path("/")
  public GetAllBooksResponse getAllBooks(
      @QueryParam("field") String field,
      @QueryParam("operator") String operator,
      @QueryParam("value") String value) {
    GetAllBooksQuery.FilterDto filter =
        GetAllBooksQuery.FilterDto.builder().field(field).operator(operator).value(value).build();
    GetAllBooksQuery query = GetAllBooksQuery.builder().filters(List.of(filter)).build();
    return getAllBooksActor.query(query);
  }
}
