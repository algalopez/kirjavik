package com.algalopez.kirjavik.havn_app.book_item.domain.port;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import java.util.List;

public interface BookItemRepositoryPort {

  List<BookItemDomainEvent> findBookItemEventsById(String id);

  void storeBookItemAddedEvent(BookItemAdded bookItemAdded);

  void storeBookItemRemovedEvent(String previousUUID, BookItemRemoved bookItemRemoved);

  void storeBookItemBorrowedEvent(String previousUUID, BookItemBorrowed bookItemBorrowed);

  void storeBookItemReturnedEvent(String previousUUID, BookItemReturned bookItemReturned);
}
