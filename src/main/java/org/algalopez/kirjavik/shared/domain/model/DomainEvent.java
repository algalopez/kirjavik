package org.algalopez.kirjavik.shared.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
@Data
@SuperBuilder
public abstract class DomainEvent {

  private final String eventId;
  private final String eventType;
  private final String aggregateId;
  private final String aggregateType;
  private final String dateTime;
}
