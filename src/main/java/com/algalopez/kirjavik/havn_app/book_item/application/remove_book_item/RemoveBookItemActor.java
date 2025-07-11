package com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemDomainEvent;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItem;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.service.BookItemReplayService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class RemoveBookItemActor {

  private final BookItemReplayService bookItemReplayService;
  private final RemoveBookItemMapper addBookItemMapper;
  private final BookItemRepositoryPort bookItemRepository;

  public void command(RemoveBookItemCommand command) {
    ensureValidUser(command.userId());
    ensureValidBook(command.bookId());
    BookItemRemoved bookItemRemoved = addBookItemMapper.mapToDomain(command);

    List<BookItemDomainEvent> bookItemEvents =
        bookItemRepository.findBookItemEventsById(command.bookId());
    ensureValidState(bookItemEvents);
    var lastEvent = bookItemEvents.getLast().getEventId();
    bookItemRepository.storeBookItemRemovedEvent(lastEvent, bookItemRemoved);
  }

  private void ensureValidUser(String userId) {
    if (userId == null) {
      throw new IllegalArgumentException("The user does not exist");
    }
  }

  private void ensureValidBook(String bookId) {
    if (bookId == null) {
      throw new IllegalArgumentException("The book does not exist");
    }
  }

  private void ensureValidState(List<BookItemDomainEvent> bookItemEvents) {
    BookItem bookItem = bookItemReplayService.replay(bookItemEvents);

    if (!BookItemStatus.AVAILABLE.equals(bookItem.getStatus())) {
      throw new IllegalArgumentException(
          "The book must be AVAILABLE to remove it, but is " + bookItem.getStatus());
    }
  }
}
