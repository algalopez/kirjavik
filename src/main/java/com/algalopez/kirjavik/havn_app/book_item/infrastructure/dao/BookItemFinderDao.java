package com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kurrent.dbclient.KurrentDBClient;
import io.kurrent.dbclient.ReadResult;
import io.kurrent.dbclient.ReadStreamOptions;
import io.kurrent.dbclient.ResolvedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@ApplicationScoped
public class BookItemFinderDao {
  private static final Long APPEND_DEADLINE = 1_000L;
  private static final String ID = "id";
  private static final String BOOK_ID = "bookId";
  private static final String USER_ID = "userId";
  private static final String EVENT_ID = "eventId";
  private static final String EVENT_TYPE = "eventType";
  private static final String AGGREGATE_ID = "aggregateId";
  private static final String AGGREGATE_TYPE = "aggregateType";
  private static final String DATE_TIME = "dateTime";

  private final KurrentDBClient kurrentDBClient;

  @SneakyThrows
  public List<BookItemDomainEvent> findBookItemEventsById(String id) {
    String streamName = BookItemDomainEvent.AGGREGATE_TYPE + "-" + id;
    ReadStreamOptions options =
        ReadStreamOptions.get().fromStart().notResolveLinkTos().deadline(APPEND_DEADLINE);
    ReadResult readResult = kurrentDBClient.readStream(streamName, options).get();
    return readResult.getEvents().stream().map(this::getBookItemDomainEvent).toList();
  }

  @SneakyThrows
  private BookItemDomainEvent getBookItemDomainEvent(ResolvedEvent event) {
    String eventType = event.getEvent().getEventType();
    byte[] eventData = event.getEvent().getEventData();
    Map<String, Object> data = new ObjectMapper().readValue(eventData, new TypeReference<>() {});
    return switch (eventType) {
      case BookItemAdded.EVENT_TYPE -> mapBookItemAddedEvent(data);
      case BookItemRemoved.EVENT_TYPE -> mapBookItemRemovedEvent(data);
      case BookItemBorrowed.EVENT_TYPE -> mapBookItemBorrowedEvent(data);
      case BookItemReturned.EVENT_TYPE -> mapBookItemReturnedEvent(data);
      default -> null;
    };
  }

  private BookItemDomainEvent mapBookItemAddedEvent(Map<String, Object> data) {
    return BookItemAdded.builder()
        .id((String) data.get(ID))
        .bookId((String) data.get(BOOK_ID))
        .userId((String) data.get(USER_ID))
        .eventId((String) data.get(EVENT_ID))
        .eventType((String) data.get(EVENT_TYPE))
        .aggregateId((String) data.get(AGGREGATE_ID))
        .aggregateType((String) data.get(AGGREGATE_TYPE))
        .dateTime((String) data.get(DATE_TIME))
        .build();
  }

  private BookItemDomainEvent mapBookItemRemovedEvent(Map<String, Object> data) {
    return BookItemRemoved.builder()
        .id((String) data.get(ID))
        .bookId((String) data.get(BOOK_ID))
        .userId((String) data.get(USER_ID))
        .eventId((String) data.get(EVENT_ID))
        .eventType((String) data.get(EVENT_TYPE))
        .aggregateId((String) data.get(AGGREGATE_ID))
        .aggregateType((String) data.get(AGGREGATE_TYPE))
        .dateTime((String) data.get(DATE_TIME))
        .build();
  }

  private BookItemDomainEvent mapBookItemBorrowedEvent(Map<String, Object> data) {
    return BookItemBorrowed.builder()
        .id((String) data.get(ID))
        .bookId((String) data.get(BOOK_ID))
        .userId((String) data.get(USER_ID))
        .eventId((String) data.get(EVENT_ID))
        .eventType((String) data.get(EVENT_TYPE))
        .aggregateId((String) data.get(AGGREGATE_ID))
        .aggregateType((String) data.get(AGGREGATE_TYPE))
        .dateTime((String) data.get(DATE_TIME))
        .build();
  }

  private BookItemDomainEvent mapBookItemReturnedEvent(Map<String, Object> data) {
    return BookItemReturned.builder()
        .id((String) data.get(ID))
        .bookId((String) data.get(BOOK_ID))
        .userId((String) data.get(USER_ID))
        .eventId((String) data.get(EVENT_ID))
        .eventType((String) data.get(EVENT_TYPE))
        .aggregateId((String) data.get(AGGREGATE_ID))
        .aggregateType((String) data.get(AGGREGATE_TYPE))
        .dateTime((String) data.get(DATE_TIME))
        .build();
  }
}
