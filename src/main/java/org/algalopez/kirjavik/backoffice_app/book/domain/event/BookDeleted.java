package org.algalopez.kirjavik.backoffice_app.book.domain.event;

import java.util.UUID;
import lombok.experimental.SuperBuilder;
import org.algalopez.kirjavik.shared.domain.model.DomainEvent;

@SuperBuilder
public class BookDeleted extends DomainEvent {

  public static final String EVENT_TYPE = "BookDeleted";
  public static final String AGGREGATE_TYPE = "Book";

  private final UUID id;
}
