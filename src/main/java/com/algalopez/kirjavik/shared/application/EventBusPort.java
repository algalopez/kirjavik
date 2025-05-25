package com.algalopez.kirjavik.shared.application;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;

public interface EventBusPort {
  void publish(DomainEvent event);
}
