package com.algalopez.kirjavik.backoffice_app.book.domain.event;

import java.util.List;
import java.util.UUID;
import lombok.experimental.SuperBuilder;
import com.algalopez.kirjavik.shared.domain.model.DomainEvent;

@SuperBuilder
public class BookCreated extends DomainEvent {

  public static final String EVENT_TYPE = "BookCreated";
  public static final String AGGREGATE_TYPE = "Book";

  private final UUID id;
  private final String isbn;
  private final String title;
  private final List<String> authors;
  private final Integer pageCount;
  private final Integer year;
}
