package com.algalopez.kirjavik.havn_app.book_item.domain.port;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;

public interface BookItemViewRepositoryPort {

  void createBookItemView(BookItemAdded bookItemAdded);

  void updateBookItemViewOnBookBorrowed(BookItemBorrowed bookItemBorrowed);

  void updateBookItemViewOnBookReturned(BookItemReturned bookItemReturned);

  void updateBookItemViewOnBookRemoved(BookItemRemoved bookItemRemoved);
}
