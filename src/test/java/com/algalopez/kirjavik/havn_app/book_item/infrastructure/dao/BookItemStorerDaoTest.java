package com.algalopez.kirjavik.havn_app.book_item.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import io.kurrent.dbclient.AppendToStreamOptions;
import io.kurrent.dbclient.EventData;
import io.kurrent.dbclient.KurrentDBClient;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class BookItemStorerDaoTest {

  private KurrentDBClient eventStoreDBClient;
  private BookItemStorerDao bookItemStorerDao;

  @BeforeEach
  void setUp() {
    eventStoreDBClient = Mockito.mock(KurrentDBClient.class);
    bookItemStorerDao = new BookItemStorerDao(eventStoreDBClient);
  }

  @Test
  void storeBookItemAddedEvent() {
    BookItemAdded bookItemAdded = new BookItemAddedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(
                Mockito.anyString(),
                Mockito.any(AppendToStreamOptions.class),
                Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemStorerDao.storeBookItemAddedEvent(bookItemAdded);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemAdded.getId()),
            Mockito.any(AppendToStreamOptions.class),
            eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemAdded");
  }

  @Test
  void storeBookItemRemovedEvent() {
    BookItemRemoved bookItemRemoved = new BookItemRemovedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(
                Mockito.anyString(),
                Mockito.any(AppendToStreamOptions.class),
                Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemStorerDao.storeBookItemRemovedEvent(bookItemRemoved);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemRemoved.getId()),
            Mockito.any(AppendToStreamOptions.class),
            eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemRemoved");
  }

  @Test
  void storeBookItemBorrowedEvent() {
    BookItemBorrowed bookItemBorrowed = new BookItemBorrowedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(
                Mockito.anyString(),
                Mockito.any(AppendToStreamOptions.class),
                Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemStorerDao.storeBookItemBorrowedEvent(bookItemBorrowed);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemBorrowed.getId()),
            Mockito.any(AppendToStreamOptions.class),
            eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemBorrowed");
  }

  @Test
  void storeBookItemReturnedEvent() {
    BookItemReturned bookItemReturned = new BookItemReturnedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(
                Mockito.anyString(),
                Mockito.any(AppendToStreamOptions.class),
                Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemStorerDao.storeBookItemReturnedEvent(bookItemReturned);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemReturned.getId()),
            Mockito.any(AppendToStreamOptions.class),
            eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemReturned");
  }
}
