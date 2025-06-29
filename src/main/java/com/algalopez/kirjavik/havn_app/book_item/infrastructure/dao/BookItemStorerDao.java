package com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kurrent.dbclient.AppendToStreamOptions;
import io.kurrent.dbclient.EventData;
import io.kurrent.dbclient.KurrentDBClient;
import io.kurrent.dbclient.StreamState;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@ApplicationScoped
public class BookItemStorerDao {
  private static final Long APPEND_DEADLINE = 1_000L;

  private final KurrentDBClient kurrentDBClient;

  @SneakyThrows
  public void storeBookItemAddedEvent(BookItemAdded bookItemAdded) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemAdded.getEventId()),
                bookItemAdded.getEventType(),
                objectMapper.writeValueAsBytes(bookItemAdded))
            .build();

    AppendToStreamOptions options =
        AppendToStreamOptions.get().streamState(StreamState.noStream()).deadline(APPEND_DEADLINE);
    String streamName = bookItemAdded.getAggregateType() + "-" + bookItemAdded.getId();

    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  public void storeBookItemRemovedEvent(Long previousRevision, BookItemRemoved bookItemRemoved) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemRemoved.getEventId()),
                bookItemRemoved.getEventType(),
                objectMapper.writeValueAsBytes(bookItemRemoved))
            .build();

    AppendToStreamOptions options =
        AppendToStreamOptions.get().streamRevision(previousRevision).deadline(APPEND_DEADLINE);
    String streamName = bookItemRemoved.getAggregateType() + "-" + bookItemRemoved.getId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  public void storeBookItemBorrowedEvent(Long previousRevision, BookItemBorrowed bookItemBorrowed) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemBorrowed.getEventId()),
                bookItemBorrowed.getEventType(),
                objectMapper.writeValueAsBytes(bookItemBorrowed))
            .build();

    AppendToStreamOptions options =
        AppendToStreamOptions.get().streamRevision(previousRevision).deadline(APPEND_DEADLINE);
    String streamName = bookItemBorrowed.getAggregateType() + "-" + bookItemBorrowed.getId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  public void storeBookItemReturnedEvent(Long previousRevision, BookItemReturned bookItemReturned) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemReturned.getEventId()),
                bookItemReturned.getEventType(),
                objectMapper.writeValueAsBytes(bookItemReturned))
            .build();

    AppendToStreamOptions options =
        AppendToStreamOptions.get().streamRevision(previousRevision).deadline(APPEND_DEADLINE);
    String streamName = bookItemReturned.getAggregateType() + "-" + bookItemReturned.getId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }
}
