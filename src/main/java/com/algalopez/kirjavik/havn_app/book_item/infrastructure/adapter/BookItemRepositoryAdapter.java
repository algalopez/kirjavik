package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.exception.OptimisticConcurrencyException;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao.BookItemFinderDao;
import com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao.BookItemStorerDao;
import com.algalopez.kirjavik.havn_app.book_item.infrastructure.model.LastEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@ApplicationScoped
public class BookItemRepositoryAdapter implements BookItemRepositoryPort {

  private final BookItemFinderDao bookItemFinderDao;
  private final BookItemStorerDao bookItemStorerDao;

  @SneakyThrows
  @Override
  public List<BookItemDomainEvent> findBookItemEventsById(String id) {
    return bookItemFinderDao.findBookItemEventsById(id);
  }

  @SneakyThrows
  @Override
  public void storeBookItemAddedEvent(BookItemAdded bookItemAdded) {
    bookItemStorerDao.storeBookItemAddedEvent(bookItemAdded);
  }

  @SneakyThrows
  @Override
  public void storeBookItemRemovedEvent(String previousUUID, BookItemRemoved bookItemRemoved) {
    LastEventMetadata lastEventMetadata =
        bookItemFinderDao.findBookItemLastEventMetadataById(bookItemRemoved.getId());
    if (!lastEventMetadata.uuid().equals(previousUUID)) {
      throw new OptimisticConcurrencyException(
          "Failed to remove book item: " + bookItemRemoved.getId());
    }

    bookItemStorerDao.storeBookItemRemovedEvent(lastEventMetadata.revision(), bookItemRemoved);
  }

  @SneakyThrows
  @Override
  public void storeBookItemBorrowedEvent(String previousUUID, BookItemBorrowed bookItemBorrowed) {
    LastEventMetadata lastEventMetadata =
        bookItemFinderDao.findBookItemLastEventMetadataById(bookItemBorrowed.getId());
    if (!lastEventMetadata.uuid().equals(previousUUID)) {
      throw new OptimisticConcurrencyException(
          "Failed to borrow book item: " + bookItemBorrowed.getId());
    }

    bookItemStorerDao.storeBookItemBorrowedEvent(lastEventMetadata.revision(), bookItemBorrowed);
  }

  @SneakyThrows
  @Override
  public void storeBookItemReturnedEvent(String previousUUID, BookItemReturned bookItemReturned) {
    LastEventMetadata lastEventMetadata =
        bookItemFinderDao.findBookItemLastEventMetadataById(bookItemReturned.getId());
    if (!lastEventMetadata.uuid().equals(previousUUID)) {
      throw new OptimisticConcurrencyException(
          "Failed to return book item: " + bookItemReturned.getId());
    }

    bookItemStorerDao.storeBookItemReturnedEvent(lastEventMetadata.revision(), bookItemReturned);
  }
}
