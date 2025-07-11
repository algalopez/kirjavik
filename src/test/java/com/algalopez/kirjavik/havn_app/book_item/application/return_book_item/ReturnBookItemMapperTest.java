package com.algalopez.kirjavik.havn_app.book_item.application.return_book_item;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class ReturnBookItemMapperTest {

  private DomainMetadataService domainMetadataService;
  private ReturnBookItemMapper mapper;

  @BeforeEach
  void setUp() {
    domainMetadataService = Mockito.mock(DomainMetadataService.class);
    mapper = Mappers.getMapper(ReturnBookItemMapper.class);
    mapper.domainMetadataService = domainMetadataService;
  }

  @Test
  void mapToDomain() {
    ReturnBookItemCommand command = new ReturnBookItemCommand("id", "bookId", "userId");
    Mockito.when(domainMetadataService.generateEventId()).thenReturn("eventId");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2023-10-01T12:00:00Z");

    BookItemReturned actualResult = mapper.mapToDomain(command);

    assertThat(actualResult)
        .isEqualTo(
            BookItemReturned.builder()
                .id(command.id())
                .bookId(command.bookId())
                .userId(command.userId())
                .eventId("eventId")
                .eventType("BookItemReturned")
                .aggregateId(command.id())
                .aggregateType("BookItem")
                .dateTime("2023-10-01T12:00:00Z")
                .build());
  }
}
