package com.algalopez.kirjavik.havn_app.user_projection.api;

import com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection.CreateUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection.CreateUserProjectionCommand;
import com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection.DeleteUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection.DeleteUserProjectionCommand;
import com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection.UpdateUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection.UpdateUserProjectionCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class UserProjectionEventListener {

  private static final String INVALID_MESSAGE = "Invalid";

  private final ObjectMapper objectMapper;
  private final CreateUserProjectionActor createUserProjectionActor;
  private final UpdateUserProjectionActor updateUserProjectionActor;
  private final DeleteUserProjectionActor deleteUserProjectionActor;

  @Incoming("havn-user-domain-events")
  @Blocking
  public void onBookEvent(JsonObject messageJson) throws JsonProcessingException {
    log.info("Received user event: {}", messageJson);
    String eventType = messageJson.getString("eventType");

    switch (eventType) {
      case UserCreatedEvent.EVENT_TYPE -> {
        UserCreatedEvent event =
            objectMapper.readValue(messageJson.encode(), UserCreatedEvent.class);
        handleUserCreated(event);
      }
      case UserUpdatedEvent.EVENT_TYPE -> {
        UserUpdatedEvent event =
            objectMapper.readValue(messageJson.encode(), UserUpdatedEvent.class);
        handleUserUpdated(event);
      }
      case UserDeletedEvent.EVENT_TYPE -> {
        UserDeletedEvent event =
            objectMapper.readValue(messageJson.encode(), UserDeletedEvent.class);
        handleUserDeleted(event);
      }

      default -> log.warn("Unhandled event type: {}. event: {}", eventType, messageJson);
    }
  }

  private void handleUserCreated(UserCreatedEvent userCreatedEvent) {
    CreateUserProjectionCommand command =
        CreateUserProjectionCommand.builder()
            .id(userCreatedEvent.getId())
            .email(userCreatedEvent.getEmail())
            .name(userCreatedEvent.getName())
            .build();
    createUserProjectionActor.command(command);
  }

  private void handleUserUpdated(UserUpdatedEvent userUpdatedEvent) {
    UpdateUserProjectionCommand command =
        UpdateUserProjectionCommand.builder()
            .id(userUpdatedEvent.getId())
            .email(userUpdatedEvent.getEmail())
            .name(userUpdatedEvent.getName())
            .build();
    updateUserProjectionActor.command(command);
  }

  private void handleUserDeleted(UserDeletedEvent userDeletedEvent) {
    DeleteUserProjectionCommand command =
        DeleteUserProjectionCommand.builder().id(userDeletedEvent.getId()).build();
    deleteUserProjectionActor.command(command);
  }
}
