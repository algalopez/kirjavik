package com.algalopez.kirjavik.backoffice_app.user.api;

import com.algalopez.kirjavik.backoffice_app.book.application.get_book.GetBookResponse;
import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserQuery;
import com.algalopez.kirjavik.backoffice_app.user.application.get_user.GetUserResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Backoffice-User")
@RequiredArgsConstructor
@Path("/backoffice/user/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserQueryController {

  private final GetUserActor getBookActor;

  @Operation(summary = "Get user by id")
  @APIResponse(
      responseCode = "200",
      content = @Content(schema = @Schema(implementation = GetBookResponse.class)))
  @GET
  @Path("/{id}")
  public GetUserResponse getUser(@PathParam("id") String id) {
    GetUserQuery query = GetUserQuery.builder().id(id).build();
    return getBookActor.query(query);
  }
}
