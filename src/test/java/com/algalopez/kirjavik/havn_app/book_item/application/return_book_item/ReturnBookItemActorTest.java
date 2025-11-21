package com.algalopez.kirjavik.havn_app.book_item.application.return_book_item;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAddedMother;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowedMother;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
import com.algalopez.kirjavik.havn_app.book_item.domain.service.BookItemReplayService;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class ReturnBookItemActorTest {

  private BookItemRepositoryPort bookItemRepository;
  private EventBusPort eventBusPort;
  private ReturnBookItemActor returnBookItemActor;

  @BeforeEach
  void setUp() {
    BookItemReplayService bookItemReplayService = new BookItemReplayService();
    bookItemRepository = Mockito.mock(BookItemRepositoryPort.class);
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    ReturnBookItemMapper returnBookItemMapper = Mappers.getMapper(ReturnBookItemMapper.class);
    returnBookItemMapper.domainMetadataService = domainMetadataService;
    eventBusPort = Mockito.mock(EventBusPort.class);
    returnBookItemActor =
        new ReturnBookItemActor(
            bookItemReplayService, returnBookItemMapper, bookItemRepository, eventBusPort);

    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2025-01-02T03:04:05");
    Mockito.when(domainMetadataService.generateEventId())
        .thenReturn("123e4567-e89b-12d3-a456-426614174000");
  }

  @Test
  void command() {
    Mockito.when(bookItemRepository.findBookItemEventsById(Mockito.anyString()))
        .thenReturn(
            List.of(new BookItemAddedMother().build(), new BookItemBorrowedMother().build()));
    ReturnBookItemCommand command =
        ReturnBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId("123e4567-e89b-12d3-a456-426614174001")
            .userId("123e4567-e89b-12d3-a456-426614174002")
            .build();

    returnBookItemActor.command(command);

    Mockito.verify(bookItemRepository)
        .storeBookItemReturnedEvent(Mockito.anyString(), Mockito.any(BookItemReturned.class));

    Mockito.verify(eventBusPort).publish(Mockito.any(BookItemBorrowed.class));
  }

  @MethodSource("command_whenInvalidRequest_source")
  @ParameterizedTest
  void command_whenInvalidRequest(String bookId, String userId) {
    ReturnBookItemCommand command =
        ReturnBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId(bookId)
            .userId(userId)
            .build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> returnBookItemActor.command(command));

    Mockito.verifyNoInteractions(bookItemRepository);
  }

  private static Stream<Arguments> command_whenInvalidRequest_source() {
    return Stream.of(
        Arguments.of(null, "123e4567-e89b-12d3-a456-426614174001"),
        Arguments.of("123e4567-e89b-12d3-a456-426614174000", null));
  }

  @Test
  void command_whenInvalidState() {
    Mockito.when(bookItemRepository.findBookItemEventsById(Mockito.anyString()))
        .thenReturn(List.of(new BookItemAddedMother().build()));
    ReturnBookItemCommand command =
        ReturnBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId("123e4567-e89b-12d3-a456-426614174001")
            .userId("123e4567-e89b-12d3-a456-426614174002")
            .build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> returnBookItemActor.command(command));
  }
}
