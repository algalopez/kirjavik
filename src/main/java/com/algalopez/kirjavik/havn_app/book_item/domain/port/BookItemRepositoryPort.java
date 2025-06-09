package com.algalopez.kirjavik.havn_app.book_item.domain.port;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;

public interface BookItemRepositoryPort {

  void storeBookItemAddedEvent(BookItemAdded bookItemAdded);

  void storeBookItemRemovedEvent(BookItemRemoved bookItemRemoved);

  void storeBookItemBorrowedEvent(BookItemBorrowed bookItemBorrowed);

  void storeBookItemReturnedEvent(BookItemReturned bookItemReturned);
}
