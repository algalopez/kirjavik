package com.algalopez.kirjavik.backoffice_app.user.api;

import com.algalopez.kirjavik.backoffice_app.user.application.create_user.CreateUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.create_user.CreateUserCommand;
import com.algalopez.kirjavik.backoffice_app.user.application.delete_user.DeleteUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.delete_user.DeleteUserCommand;
import com.algalopez.kirjavik.backoffice_app.user.application.update_user.UpdateUserActor;
import com.algalopez.kirjavik.backoffice_app.user.application.update_user.UpdateUserCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Backoffice-User")
@RequiredArgsConstructor
@Path("/backoffice/user/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserCommandController {

  private final CreateUserActor createUserActor;
  private final UpdateUserActor updateUserActor;
  private final DeleteUserActor deleteUserActor;

  @Operation(summary = "Create user")
  @APIResponse(responseCode = "204")
  @POST
  @Path("/")
  public void createUser(CreateUserCommand command) {
    createUserActor.command(command);
  }

  @Operation(summary = "Update user")
  @APIResponse(responseCode = "204")
  @PUT
  @Path("/{id}")
  public void updateUser(@PathParam("id") String id, UpdateUserCommand command) {
    UpdateUserCommand commandWithId = command.toBuilder().id(id).build();
    updateUserActor.command(commandWithId);
  }

  @Operation(summary = "Delete user by id")
  @APIResponse(responseCode = "204")
  @DELETE
  @Path("/{id}")
  public void deleteUser(@PathParam("id") String id) {
    DeleteUserCommand command = DeleteUserCommand.builder().id(id).build();
    deleteUserActor.command(command);
  }
}
