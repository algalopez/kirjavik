package com.algalopez.kirjavik.backoffice_app.book.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookDeleted extends DomainEvent {

  public static final String EVENT_TYPE = "BookDeleted";
  public static final String AGGREGATE_TYPE = "Book";

  private final UUID id;
}
