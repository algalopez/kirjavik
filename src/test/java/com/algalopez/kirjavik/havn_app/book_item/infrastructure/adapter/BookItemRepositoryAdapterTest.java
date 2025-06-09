package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import java.util.concurrent.CompletableFuture;

import io.kurrent.dbclient.EventData;
import io.kurrent.dbclient.KurrentDBClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class BookItemRepositoryAdapterTest {

  private KurrentDBClient eventStoreDBClient;
  private BookItemRepositoryAdapter bookItemRepositoryAdapter;

  @BeforeEach
  void setUp() {
    eventStoreDBClient = Mockito.mock(KurrentDBClient.class);
    bookItemRepositoryAdapter = new BookItemRepositoryAdapter(eventStoreDBClient);
  }

  @Test
  void storeBookItemAddedEvent() {
    BookItemAdded bookItemAdded = new BookItemAddedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(Mockito.anyString(), Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemRepositoryAdapter.storeBookItemAddedEvent(bookItemAdded);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemAdded.getBookId()), eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemAdded");
  }

  @Test
  void storeBookItemRemovedEvent() {
    BookItemRemoved bookItemRemoved = new BookItemRemovedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(Mockito.anyString(), Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemRepositoryAdapter.storeBookItemRemovedEvent(bookItemRemoved);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemRemoved.getBookId()), eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemRemoved");
  }

  @Test
  void storeBookItemBorrowedEvent() {
    BookItemBorrowed bookItemBorrowed = new BookItemBorrowedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(Mockito.anyString(), Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemRepositoryAdapter.storeBookItemBorrowedEvent(bookItemBorrowed);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemBorrowed.getBookId()), eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemBorrowed");
  }

  @Test
  void storeBookItemReturnedEvent() {
    BookItemReturned bookItemReturned = new BookItemReturnedMother().build();
    Mockito.when(
            eventStoreDBClient.appendToStream(Mockito.anyString(), Mockito.any(EventData.class)))
        .thenReturn(CompletableFuture.completedFuture(null));

    bookItemRepositoryAdapter.storeBookItemReturnedEvent(bookItemReturned);

    ArgumentCaptor<EventData> eventDataCaptor = ArgumentCaptor.forClass(EventData.class);
    Mockito.verify(eventStoreDBClient)
        .appendToStream(
            Mockito.eq("BookItem-" + bookItemReturned.getBookId()), eventDataCaptor.capture());
    assertThat(eventDataCaptor.getValue().getEventType()).isEqualTo("BookItemReturned");
  }
}
