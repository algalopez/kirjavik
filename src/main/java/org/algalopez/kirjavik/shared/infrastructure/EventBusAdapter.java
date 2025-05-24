package org.algalopez.kirjavik.shared.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.algalopez.kirjavik.shared.application.EventBusPort;
import org.algalopez.kirjavik.shared.domain.model.DomainEvent;

@Slf4j
@ApplicationScoped
public class EventBusAdapter implements EventBusPort {
  @Override
  public void publish(DomainEvent event) {
    log.info("Publishing event: {}", event);
  }
}
