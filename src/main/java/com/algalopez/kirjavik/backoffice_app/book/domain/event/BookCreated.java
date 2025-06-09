package com.algalopez.kirjavik.backoffice_app.book.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookCreated extends DomainEvent {

  public static final String EVENT_TYPE = "BookCreated";
  public static final String AGGREGATE_TYPE = "Book";

  private final UUID id;
  private final String isbn;
  private final String title;
  private final String author;
  private final Integer pageCount;
  private final Integer year;
}
