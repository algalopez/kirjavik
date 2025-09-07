package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserCreated;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateUserEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishUserCreatedEvent(User user) {
    UserCreated userCreated =
        UserCreated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(UserCreated.EVENT_TYPE)
            .aggregateId(user.getId().toString())
            .aggregateType(UserCreated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();

    eventBusPort.publish(userCreated);
  }
}
