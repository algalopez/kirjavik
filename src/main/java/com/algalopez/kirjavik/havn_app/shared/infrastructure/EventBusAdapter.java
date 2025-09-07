package com.algalopez.kirjavik.havn_app.shared.infrastructure;

import com.algalopez.kirjavik.havn_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class EventBusAdapter implements EventBusPort {

  @Override
  public void publish(DomainEvent event) {
    log.info("Publishing event {}", event);
  }
}
