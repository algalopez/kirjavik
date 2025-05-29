package com.algalopez.kirjavik.backoffice_app.user.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.util.UUID;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class UserDeleted extends DomainEvent {

  public static final String EVENT_TYPE = "UserDeleted";
  public static final String AGGREGATE_TYPE = "User";

  private final UUID id;
}
