package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kurrent.dbclient.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@ApplicationScoped
public class BookItemRepositoryAdapter implements BookItemRepositoryPort {
  private static final Long APPEND_DEADLINE = 1_000L;

  private final KurrentDBClient kurrentDBClient;

  @SneakyThrows
  @Override
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
    String streamName = bookItemAdded.getAggregateType() + "-" + bookItemAdded.getBookId();

    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  @Override
  public void storeBookItemRemovedEvent(BookItemRemoved bookItemRemoved) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemRemoved.getEventId()),
                bookItemRemoved.getEventType(),
                objectMapper.writeValueAsBytes(bookItemRemoved))
            .build();

    AppendToStreamOptions options = AppendToStreamOptions.get().deadline(APPEND_DEADLINE);
    String streamName = bookItemRemoved.getAggregateType() + "-" + bookItemRemoved.getBookId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  @Override
  public void storeBookItemBorrowedEvent(BookItemBorrowed bookItemBorrowed) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemBorrowed.getEventId()),
                bookItemBorrowed.getEventType(),
                objectMapper.writeValueAsBytes(bookItemBorrowed))
            .build();

    AppendToStreamOptions options = AppendToStreamOptions.get().deadline(APPEND_DEADLINE);
    String streamName = bookItemBorrowed.getAggregateType() + "-" + bookItemBorrowed.getBookId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }

  @SneakyThrows
  @Override
  public void storeBookItemReturnedEvent(BookItemReturned bookItemReturned) {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(bookItemReturned.getEventId()),
                bookItemReturned.getEventType(),
                objectMapper.writeValueAsBytes(bookItemReturned))
            .build();

    AppendToStreamOptions options = AppendToStreamOptions.get().deadline(APPEND_DEADLINE);
    String streamName = bookItemReturned.getAggregateType() + "-" + bookItemReturned.getBookId();
    kurrentDBClient.appendToStream(streamName, options, eventData).get();
  }
}
