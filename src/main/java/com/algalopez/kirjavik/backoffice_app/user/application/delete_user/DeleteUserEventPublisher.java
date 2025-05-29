package com.algalopez.kirjavik.backoffice_app.user.application.delete_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserDeleted;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteUserEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishUserDeletedEvent(UUID userId) {
    UserDeleted userDeleted =
        UserDeleted.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(UserDeleted.EVENT_TYPE)
            .aggregateId(userId.toString())
            .aggregateType(UserDeleted.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .build();

    eventBusPort.publish(userDeleted);
  }
}
