package com.algalopez.kirjavik.havn_app.book_item.application.add_book_item;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class AddBookItemMapperTest {

  private DomainMetadataService domainMetadataService;
  private AddBookItemMapper mapper;

  @BeforeEach
  void setUp() {
    domainMetadataService = Mockito.mock(DomainMetadataService.class);
    mapper = Mappers.getMapper(AddBookItemMapper.class);
    mapper.domainMetadataService = domainMetadataService;
  }

  @Test
  void mapToDomain() {
    AddBookItemCommand command = new AddBookItemCommand("id", "bookId", "userId");
    Mockito.when(domainMetadataService.generateEventId()).thenReturn("eventId");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2023-10-01T12:00:00Z");

    BookItemAdded actualResult = mapper.mapToDomain(command);

    assertThat(actualResult)
        .isEqualTo(
            BookItemAdded.builder()
                .id(command.id())
                .bookId(command.bookId())
                .userId(command.userId())
                .eventId("eventId")
                .eventType("BookItemAdded")
                .aggregateId(command.id())
                .aggregateType("BookItem")
                .dateTime("2023-10-01T12:00:00Z")
                .build());
  }
}
