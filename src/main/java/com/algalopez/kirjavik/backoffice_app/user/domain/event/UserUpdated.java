package com.algalopez.kirjavik.backoffice_app.user.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.util.UUID;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class UserUpdated extends DomainEvent {

  public static final String EVENT_TYPE = "UserUpdated";
  public static final String AGGREGATE_TYPE = "User";

  private final UUID id;
  private final String name;
  private final String email;
}
