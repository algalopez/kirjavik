package com.algalopez.kirjavik.backoffice_app.user.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class UserCreated extends DomainEvent {

  public static final String EVENT_TYPE = "UserCreated";
  public static final String AGGREGATE_TYPE = "User";

  private final UUID id;
  private final String name;
  private final String email;
}
