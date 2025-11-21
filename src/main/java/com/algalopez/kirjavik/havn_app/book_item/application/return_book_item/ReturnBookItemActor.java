package com.algalopez.kirjavik.havn_app.book_item.application.return_book_item;

import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemDomainEvent;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItem;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.service.BookItemReplayService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class ReturnBookItemActor {

  private final BookItemReplayService bookItemReplayService;
  private final ReturnBookItemMapper returnBookItemMapper;
  private final BookItemRepositoryPort bookItemRepository;
  private final EventBusPort eventBusPort;

  public void command(ReturnBookItemCommand command) {
    ensureValidUser(command.userId());
    ensureValidBook(command.bookId());
    BookItemReturned bookItemReturned = returnBookItemMapper.mapToDomain(command);

    List<BookItemDomainEvent> bookItemEvents =
        bookItemRepository.findBookItemEventsById(command.bookId());
    ensureValidState(bookItemEvents);
    var lastEvent = bookItemEvents.getLast().getEventId();
    bookItemRepository.storeBookItemReturnedEvent(lastEvent, bookItemReturned);

    eventBusPort.publish(bookItemReturned);
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

    if (!BookItemStatus.BORROWED.equals(bookItem.getStatus())) {
      throw new IllegalArgumentException(
          "The book must be BORROWED to return it, but is " + bookItem.getStatus());
    }
  }
}
