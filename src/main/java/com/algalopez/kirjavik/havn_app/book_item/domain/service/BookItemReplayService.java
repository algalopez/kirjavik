package com.algalopez.kirjavik.havn_app.book_item.domain.service;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.exception.ReplayEventException;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItem;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public final class BookItemReplayService {

  public BookItem replay(List<BookItemDomainEvent> events) {
    return events.stream().reduce(null, this::applyEvent, (a, b) -> b);
  }

  private BookItem applyEvent(BookItem bookItem, BookItemDomainEvent event) {
    return switch (event) {
      case BookItemAdded bookItemAdded -> apply(bookItem, bookItemAdded);
      case BookItemBorrowed bookItemBorrowed -> apply(bookItem, bookItemBorrowed);
      case BookItemReturned bookItemReturned -> apply(bookItem, bookItemReturned);
      case BookItemRemoved bookItemRemoved -> apply(bookItem, bookItemRemoved);
    };
  }

  private BookItem apply(BookItem bookItem, BookItemAdded bookItemAdded) {
    ensureFirstEvent(bookItem, bookItemAdded);
    return BookItem.builder()
        .id(UUID.fromString(bookItemAdded.getId()))
        .bookId(UUID.fromString(bookItemAdded.getBookId()))
        .userId(UUID.fromString(bookItemAdded.getUserId()))
        .status(BookItemStatus.AVAILABLE)
        .build();
  }

  private BookItem apply(BookItem bookItem, BookItemBorrowed event) {
    ensureNotFirstEvent(bookItem, event);
    ensurePreviousStates(bookItem, event, BookItemStatus.AVAILABLE);
    bookItem.setStatus(BookItemStatus.BORROWED);
    return bookItem;
  }

  private BookItem apply(BookItem bookItem, BookItemReturned event) {
    ensureNotFirstEvent(bookItem, event);
    ensurePreviousStates(bookItem, event, BookItemStatus.BORROWED);
    bookItem.setStatus(BookItemStatus.AVAILABLE);
    return bookItem;
  }

  private BookItem apply(BookItem bookItem, BookItemRemoved event) {
    ensureNotFirstEvent(bookItem, event);
    ensurePreviousStates(bookItem, event, BookItemStatus.AVAILABLE);
    bookItem.setStatus(BookItemStatus.REMOVED);
    return bookItem;
  }

  private void ensureFirstEvent(BookItem bookItem, BookItemDomainEvent event) {
    if (bookItem != null) {
      throw new ReplayEventException("There should not be a previous event", bookItem, event);
    }
  }

  private void ensureNotFirstEvent(BookItem bookItem, BookItemDomainEvent event) {
    if (bookItem == null) {
      throw new ReplayEventException("There should be a previous event", bookItem, event);
    }
  }

  private void ensurePreviousStates(
      BookItem bookItem, BookItemDomainEvent event, BookItemStatus... previousStatuses) {
    if (Arrays.stream(previousStatuses).noneMatch(s -> s.equals(bookItem.getStatus()))) {
      throw new ReplayEventException(
          "The book item status should be one of the previous states: "
              + Arrays.toString(previousStatuses),
          bookItem,
          event);
    }
  }
}
