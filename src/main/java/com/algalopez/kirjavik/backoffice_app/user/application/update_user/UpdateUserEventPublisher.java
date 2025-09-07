package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserUpdated;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateUserEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishUserUpdatedEvent(User user) {
    UserUpdated userUpdated =
        UserUpdated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(UserUpdated.EVENT_TYPE)
            .aggregateId(user.getId().toString())
            .aggregateType(UserUpdated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();

    eventBusPort.publish(userUpdated);
  }
}
