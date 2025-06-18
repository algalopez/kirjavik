package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao.BookItemFinderDao;
import com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao.BookItemStorerDao;
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
  public void storeBookItemRemovedEvent(BookItemRemoved bookItemRemoved) {
    bookItemStorerDao.storeBookItemRemovedEvent(bookItemRemoved);
  }

  @SneakyThrows
  @Override
  public void storeBookItemBorrowedEvent(BookItemBorrowed bookItemBorrowed) {
    bookItemStorerDao.storeBookItemBorrowedEvent(bookItemBorrowed);
  }

  @SneakyThrows
  @Override
  public void storeBookItemReturnedEvent(BookItemReturned bookItemReturned) {
    bookItemStorerDao.storeBookItemReturnedEvent(bookItemReturned);
  }
}
