package com.algalopez.kirjavik.havn_app.book_item.application.add_book_item;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAddedMother;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemRepositoryPort;
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

class AddBookItemActorTest {

  private BookItemRepositoryPort bookItemRepository;
  private AddBookItemActor addBookItemActor;

  @BeforeEach
  void setUp() {
    bookItemRepository = Mockito.mock(BookItemRepositoryPort.class);
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    AddBookItemMapper addBookItemMapper = Mappers.getMapper(AddBookItemMapper.class);
    addBookItemMapper.domainMetadataService = domainMetadataService;
    addBookItemActor = new AddBookItemActor(addBookItemMapper, bookItemRepository);

    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2025-01-02T03:04:05");
    Mockito.when(domainMetadataService.generateEventId())
        .thenReturn("123e4567-e89b-12d3-a456-426614174000");
  }

  @Test
  void command() {
    Mockito.when(bookItemRepository.findBookItemEventsById(Mockito.anyString()))
        .thenReturn(List.of());
    AddBookItemCommand command =
        AddBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId("123e4567-e89b-12d3-a456-426614174001")
            .userId("123e4567-e89b-12d3-a456-426614174002")
            .build();

    addBookItemActor.command(command);

    Mockito.verify(bookItemRepository).storeBookItemAddedEvent(Mockito.any(BookItemAdded.class));
  }

  @MethodSource("command_whenInvalidRequest_source")
  @ParameterizedTest
  void command_whenInvalidRequest(String bookId, String userId) {
    Mockito.when(bookItemRepository.findBookItemEventsById(Mockito.anyString()))
        .thenReturn(List.of());
    AddBookItemCommand command =
        AddBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId(bookId)
            .userId(userId)
            .build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> addBookItemActor.command(command));

    Mockito.verifyNoInteractions(bookItemRepository);
  }

  private static Stream<Arguments> command_whenInvalidRequest_source() {
    return Stream.of(
        Arguments.of(null, "123e4567-e89b-12d3-a456-426614174001"),
        Arguments.of("123e4567-e89b-12d3-a456-426614174000", null));
  }

  @Test
  void command_whenNotFirstEvent() {
    Mockito.when(bookItemRepository.findBookItemEventsById(Mockito.anyString()))
        .thenReturn(List.of(new BookItemAddedMother().build()));
    AddBookItemCommand command =
        AddBookItemCommand.builder()
            .id("123e4567-e89b-12d3-a456-426614174000")
            .bookId("123e4567-e89b-12d3-a456-426614174001")
            .userId("123e4567-e89b-12d3-a456-426614174002")
            .build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> addBookItemActor.command(command));

    Mockito.verify(bookItemRepository, Mockito.never())
        .storeBookItemAddedEvent(Mockito.any(BookItemAdded.class));
  }
}
