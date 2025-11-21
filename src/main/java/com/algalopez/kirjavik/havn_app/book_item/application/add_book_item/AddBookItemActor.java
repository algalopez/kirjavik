package com.algalopez.kirjavik.havn_app.book_item.application.add_book_item;

import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemDomainEvent;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class AddBookItemActor {

  private final AddBookItemMapper addBookItemMapper;
  private final BookItemRepositoryPort bookItemRepository;
  private final EventBusPort eventBusPort;

  public void command(AddBookItemCommand command) {
    ensureValidUser(command.userId());
    ensureValidBook(command.bookId());
    BookItemAdded bookItemAdded = addBookItemMapper.mapToDomain(command);

    List<BookItemDomainEvent> bookItemEvents =
        bookItemRepository.findBookItemEventsById(command.bookId());
    ensureFirstEvent(bookItemEvents);
    bookItemRepository.storeBookItemAddedEvent(bookItemAdded);

    eventBusPort.publish(bookItemAdded);
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

  private void ensureFirstEvent(List<BookItemDomainEvent> bookItemEvents) {
    if (!bookItemEvents.isEmpty()) {
      throw new IllegalArgumentException("The book already exists");
    }
  }
}
