package com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class RemoveBookItemMapperTest {

  private DomainMetadataService domainMetadataService;
  private RemoveBookItemMapper mapper;

  @BeforeEach
  void setUp() {
    domainMetadataService = Mockito.mock(DomainMetadataService.class);
    mapper = Mappers.getMapper(RemoveBookItemMapper.class);
    mapper.domainMetadataService = domainMetadataService;
  }

  @Test
  void mapToDomain() {
    RemoveBookItemCommand command = new RemoveBookItemCommand("id", "bookId", "userId");
    Mockito.when(domainMetadataService.generateEventId()).thenReturn("eventId");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2023-10-01T12:00:00Z");

    BookItemRemoved actualResult = mapper.mapToDomain(command);

    assertThat(actualResult)
        .isEqualTo(
            BookItemRemoved.builder()
                .id(command.id())
                .bookId(command.bookId())
                .userId(command.userId())
                .eventId("eventId")
                .eventType("BookItemRemoved")
                .aggregateId(command.id())
                .aggregateType("BookItem")
                .dateTime("2023-10-01T12:00:00Z")
                .build());
  }
}
