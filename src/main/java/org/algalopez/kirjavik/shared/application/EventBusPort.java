package org.algalopez.kirjavik.shared.application;

import org.algalopez.kirjavik.shared.domain.model.DomainEvent;

public interface EventBusPort {
  void publish(DomainEvent event);
}
