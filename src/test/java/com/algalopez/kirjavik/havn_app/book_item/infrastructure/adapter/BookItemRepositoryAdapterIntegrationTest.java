package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kurrent.dbclient.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@QuarkusTest
class BookItemRepositoryAdapterIntegrationTest {

  private static final String BOOK_ITEM_ID = "1234";
  private static final String FIELD_USER_ID = "userId";
  private static final String FIELD_ID = "id";
  private static final String FIELD_BOOK_ID = "bookId";
  private static final String FIELD_DATE_TIME = "dateTime";
  private static final String FIELD_AGGREGATE_TYPE = "aggregateType";
  private static final String FIELD_AGGREGATE_ID = "aggregateId";
  private static final String FIELD_EVENT_TYPE = "eventType";
  private static final String FIELD_EVENT_ID = "eventId";

  @Inject KurrentDBClient kurrentDBClient;
  @Inject BookItemRepositoryAdapter bookItemRepositoryAdapter;

  @AfterEach
  void tearDown() throws ExecutionException, InterruptedException {
    kurrentDBClient.deleteStream("BookItem-" + BOOK_ITEM_ID).get();
  }

  @MethodSource("findBookItemById_source")
  @ParameterizedTest
  void findBookItemById(BookItemDomainEvent event)
      throws JsonProcessingException, ExecutionException, InterruptedException {
    ObjectMapper objectMapper = new ObjectMapper();
    EventData eventData =
        EventData.builderAsJson(
                UUID.fromString(event.getEventId()),
                event.getEventType(),
                objectMapper.writeValueAsBytes(event))
            .build();
    kurrentDBClient.appendToStream("BookItem-" + BOOK_ITEM_ID, eventData).get();

    List<BookItemDomainEvent> actualEvents =
        bookItemRepositoryAdapter.findBookItemEventsById(BOOK_ITEM_ID);

    assertThat(actualEvents).hasSize(1);
    assertThat(actualEvents.getFirst()).isInstanceOf(event.getClass());
    assertThat(actualEvents.getFirst()).isEqualTo(event);
  }

  private static Stream<Arguments> findBookItemById_source() {
    BookItemAdded bookItemAdded = new BookItemAddedMother().id(BOOK_ITEM_ID).build();
    BookItemRemoved bookItemRemoved = new BookItemRemovedMother().id(BOOK_ITEM_ID).build();
    BookItemBorrowed bookItemBorrowed = new BookItemBorrowedMother().id(BOOK_ITEM_ID).build();
    BookItemReturned bookItemReturned = new BookItemReturnedMother().id(BOOK_ITEM_ID).build();
    return Stream.of(
        Arguments.of(bookItemAdded),
        Arguments.of(bookItemRemoved),
        Arguments.of(bookItemBorrowed),
        Arguments.of(bookItemReturned));
  }

  @Test
  void storeBookItemAddedEvent() throws ExecutionException, InterruptedException {
    BookItemAdded bookItemAdded = new BookItemAddedMother().id(BOOK_ITEM_ID).build();
    String expectedStreamName = "BookItem-" + bookItemAdded.getId();

    bookItemRepositoryAdapter.storeBookItemAddedEvent(bookItemAdded);

    ReadResult result =
        kurrentDBClient
            .readStream(expectedStreamName, ReadStreamOptions.get().fromStart().notResolveLinkTos())
            .get();
    assertThat(result.getEvents()).hasSize(1);
    assertThat(result.getEvents().getFirst().getEvent().getEventType())
        .isEqualTo(bookItemAdded.getEventType());
    Map<String, Object> actualEvent = mapToMap(result.getEvents().getFirst());
    assertThat(actualEvent)
        .containsEntry(FIELD_EVENT_ID, bookItemAdded.getEventId())
        .containsEntry(FIELD_EVENT_TYPE, bookItemAdded.getEventType())
        .containsEntry(FIELD_AGGREGATE_ID, bookItemAdded.getAggregateId())
        .containsEntry(FIELD_AGGREGATE_TYPE, bookItemAdded.getAggregateType())
        .containsEntry(FIELD_DATE_TIME, bookItemAdded.getDateTime())
        .containsEntry(FIELD_ID, bookItemAdded.getId())
        .containsEntry(FIELD_BOOK_ID, bookItemAdded.getBookId())
        .containsEntry(FIELD_USER_ID, bookItemAdded.getUserId());
  }

  @Test
  void storeBookItemRemovedEvent() throws ExecutionException, InterruptedException {
    BookItemRemoved bookItemRemoved = new BookItemRemovedMother().id(BOOK_ITEM_ID).build();
    String expectedStreamName = "BookItem-" + bookItemRemoved.getId();

    bookItemRepositoryAdapter.storeBookItemRemovedEvent(bookItemRemoved);

    ReadResult result =
        kurrentDBClient
            .readStream(expectedStreamName, ReadStreamOptions.get().fromStart().notResolveLinkTos())
            .get();
    assertThat(result.getEvents()).hasSize(1);
    assertThat(result.getEvents().getFirst().getEvent().getEventType())
        .isEqualTo(bookItemRemoved.getEventType());
    Map<String, Object> actualEvent = mapToMap(result.getEvents().getFirst());
    assertThat(actualEvent)
        .containsEntry(FIELD_EVENT_ID, bookItemRemoved.getEventId())
        .containsEntry(FIELD_EVENT_TYPE, bookItemRemoved.getEventType())
        .containsEntry(FIELD_AGGREGATE_ID, bookItemRemoved.getAggregateId())
        .containsEntry(FIELD_AGGREGATE_TYPE, bookItemRemoved.getAggregateType())
        .containsEntry(FIELD_DATE_TIME, bookItemRemoved.getDateTime())
        .containsEntry(FIELD_ID, bookItemRemoved.getId())
        .containsEntry(FIELD_BOOK_ID, bookItemRemoved.getBookId())
        .containsEntry(FIELD_USER_ID, bookItemRemoved.getUserId());
  }

  @Test
  void storeBookItemBorrowedEvent() throws ExecutionException, InterruptedException {
    BookItemBorrowed bookItemBorrowed = new BookItemBorrowedMother().id(BOOK_ITEM_ID).build();
    String expectedStreamName = "BookItem-" + bookItemBorrowed.getId();

    bookItemRepositoryAdapter.storeBookItemBorrowedEvent(bookItemBorrowed);

    ReadResult result =
        kurrentDBClient
            .readStream(expectedStreamName, ReadStreamOptions.get().fromStart().notResolveLinkTos())
            .get();
    assertThat(result.getEvents()).hasSize(1);
    assertThat(result.getEvents().getFirst().getEvent().getEventType())
        .isEqualTo(bookItemBorrowed.getEventType());
    Map<String, Object> actualEvent = mapToMap(result.getEvents().getFirst());
    assertThat(actualEvent)
        .containsEntry(FIELD_EVENT_ID, bookItemBorrowed.getEventId())
        .containsEntry(FIELD_EVENT_TYPE, bookItemBorrowed.getEventType())
        .containsEntry(FIELD_AGGREGATE_ID, bookItemBorrowed.getAggregateId())
        .containsEntry(FIELD_AGGREGATE_TYPE, bookItemBorrowed.getAggregateType())
        .containsEntry(FIELD_DATE_TIME, bookItemBorrowed.getDateTime())
        .containsEntry(FIELD_ID, bookItemBorrowed.getId())
        .containsEntry(FIELD_BOOK_ID, bookItemBorrowed.getBookId())
        .containsEntry(FIELD_USER_ID, bookItemBorrowed.getUserId());
  }

  @Test
  void storeBookItemReturnedEvent() throws ExecutionException, InterruptedException {
    BookItemReturned bookItemReturned = new BookItemReturnedMother().id(BOOK_ITEM_ID).build();
    String expectedStreamName = "BookItem-" + bookItemReturned.getId();

    bookItemRepositoryAdapter.storeBookItemReturnedEvent(bookItemReturned);

    ReadResult result =
        kurrentDBClient
            .readStream(expectedStreamName, ReadStreamOptions.get().fromStart().notResolveLinkTos())
            .get();
    assertThat(result.getEvents()).hasSize(1);
    assertThat(result.getEvents().getFirst().getEvent().getEventType())
        .isEqualTo(bookItemReturned.getEventType());
    Map<String, Object> actualEvent = mapToMap(result.getEvents().getFirst());
    assertThat(actualEvent)
        .containsEntry(FIELD_EVENT_ID, bookItemReturned.getEventId())
        .containsEntry(FIELD_EVENT_TYPE, bookItemReturned.getEventType())
        .containsEntry(FIELD_AGGREGATE_ID, bookItemReturned.getAggregateId())
        .containsEntry(FIELD_AGGREGATE_TYPE, bookItemReturned.getAggregateType())
        .containsEntry(FIELD_DATE_TIME, bookItemReturned.getDateTime())
        .containsEntry(FIELD_ID, bookItemReturned.getId())
        .containsEntry(FIELD_BOOK_ID, bookItemReturned.getBookId())
        .containsEntry(FIELD_USER_ID, bookItemReturned.getUserId());
  }

  @SneakyThrows
  private static Map<String, Object> mapToMap(ResolvedEvent event) {
    return new ObjectMapper().readValue(event.getEvent().getEventData(), new TypeReference<>() {});
  }
}
