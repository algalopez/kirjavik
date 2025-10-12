package com.algalopez.kirjavik.havn_app.user_projection.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection.CreateUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection.CreateUserProjectionCommand;
import com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection.DeleteUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection.DeleteUserProjectionCommand;
import com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection.UpdateUserProjectionActor;
import com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection.UpdateUserProjectionCommand;
import com.algalopez.kirjavik.shared.infrastructure.RabbitMqTestClient;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class UserProjectionEventListenerApiTest {

  private static final String EXCHANGE_NAME = "kirjavik.backoffice.domain-events";

  @InjectMock CreateUserProjectionActor createUserProjectionActor;
  @InjectMock UpdateUserProjectionActor updateUserProjectionActor;
  @InjectMock DeleteUserProjectionActor deleteUserProjectionActor;
  @Inject RabbitMqTestClient rabbitMqTestClient;

  @Test
  void onBookEvent_whenBookCreated() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "UserCreated",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "User",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "email": "name@domain.com",
          "name": "Name"
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.user.UserCreated",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(createUserProjectionActor, Mockito.timeout(5000))
        .command(
            CreateUserProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .email("name@domain.com")
                .name("Name")
                .build());
  }

  @Test
  void onBookEvent_whenBookUpdated() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "UserUpdated",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "User",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "email": "name@domain.com",
          "name": "Name"
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.user.UserUpdated",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(updateUserProjectionActor, Mockito.timeout(5000))
        .command(
            UpdateUserProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .email("name@domain.com")
                .name("Name")
                .build());
  }

  @Test
  void onBookEvent_whenBookDeleted() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "UserDeleted",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "User",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a"
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.user.UserDeleted",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(deleteUserProjectionActor, Mockito.timeout(5000))
        .command(
            DeleteUserProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .build());
  }

  @Test
  void onBookEvent_whenInvalidEventType() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "Invalid",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "User",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a"
        }
        """;

    assertThatNoException()
        .isThrownBy(
            () ->
                rabbitMqTestClient.publishMessage(
                    EXCHANGE_NAME,
                    "kirjavik.backoffice.user.UserDeleted",
                    eventJson.getBytes(StandardCharsets.UTF_8)));
  }
}
