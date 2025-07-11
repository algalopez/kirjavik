package com.algalopez.kirjavik.havn_app.book_item.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.exception.ReplayEventException;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItem;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BookItemReplayServiceTest {

  private static final UUID ID = UUID.fromString("123e4567-e89b-0000-0000-000000000000");
  private static final UUID BOOK_ID = UUID.fromString("123e4567-e89b-0000-0000-000000000001");
  private static final UUID USER_ID = UUID.fromString("123e4567-e89b-0000-0000-000000000002");

  private final BookItemReplayService bookItemReplayService = new BookItemReplayService();

  @MethodSource("replay_whenOk_source")
  @ParameterizedTest
  void replay_whenOk(List<BookItemDomainEvent> events, BookItemStatus expectedStatus) {
    BookItem actualResult = bookItemReplayService.replay(events);

    assertThat(actualResult)
        .isEqualTo(
            BookItem.builder()
                .id(ID)
                .bookId(BOOK_ID)
                .userId(USER_ID)
                .status(expectedStatus)
                .build());
  }

  private static Stream<Arguments> replay_whenOk_source() {
    BookItemDomainEvent addedEvent = buildAddedEvent();
    BookItemDomainEvent borrowedEvent = buildBorrowedEvent();
    BookItemDomainEvent returnedEvent = buildReturnedEvent();
    BookItemDomainEvent removedEvent = buildRemovedEvent();

    return Stream.of(
        Arguments.of(List.of(addedEvent), BookItemStatus.AVAILABLE),
        Arguments.of(List.of(addedEvent, borrowedEvent), BookItemStatus.BORROWED),
        Arguments.of(List.of(addedEvent, borrowedEvent, returnedEvent), BookItemStatus.AVAILABLE),
        Arguments.of(
            List.of(addedEvent, borrowedEvent, returnedEvent, removedEvent),
            BookItemStatus.REMOVED));
  }

  @MethodSource("replay_whenInvalidEvent_source")
  @ParameterizedTest
  void replay_whenInvalidEvent_(List<BookItemDomainEvent> events) {

    assertThatExceptionOfType(ReplayEventException.class)
        .isThrownBy(() -> bookItemReplayService.replay(events));
  }

  private static Stream<Arguments> replay_whenInvalidEvent_source() {
    BookItemDomainEvent addedEvent = buildAddedEvent();
    BookItemDomainEvent borrowedEvent = buildBorrowedEvent();
    BookItemDomainEvent returnedEvent = buildReturnedEvent();
    BookItemDomainEvent removedEvent = buildRemovedEvent();

    return Stream.of(
        Arguments.of(List.of(borrowedEvent)),
        Arguments.of(List.of(returnedEvent)),
        Arguments.of(List.of(removedEvent)),
        Arguments.of(List.of(addedEvent, returnedEvent)),
        Arguments.of(List.of(addedEvent, borrowedEvent, removedEvent)),
        Arguments.of(List.of(addedEvent, borrowedEvent, borrowedEvent)),
        Arguments.of(List.of(addedEvent, returnedEvent, returnedEvent)),
        Arguments.of(List.of(addedEvent, removedEvent, addedEvent)),
        Arguments.of(List.of(addedEvent, removedEvent, borrowedEvent)),
        Arguments.of(List.of(addedEvent, removedEvent, returnedEvent)),
        Arguments.of(List.of(addedEvent, removedEvent, removedEvent)));
  }

  private static BookItemDomainEvent buildAddedEvent() {
    return new BookItemAddedMother()
        .id(ID.toString())
        .bookId(BOOK_ID.toString())
        .userId(USER_ID.toString())
        .build();
  }

  private static BookItemDomainEvent buildBorrowedEvent() {
    return new BookItemBorrowedMother()
        .id(ID.toString())
        .bookId(BOOK_ID.toString())
        .userId(USER_ID.toString())
        .build();
  }

  private static BookItemDomainEvent buildReturnedEvent() {
    return new BookItemReturnedMother()
        .id(ID.toString())
        .bookId(BOOK_ID.toString())
        .userId(USER_ID.toString())
        .build();
  }

  private static BookItemDomainEvent buildRemovedEvent() {
    return new BookItemRemovedMother()
        .id(ID.toString())
        .bookId(BOOK_ID.toString())
        .userId(USER_ID.toString())
        .build();
  }
}
