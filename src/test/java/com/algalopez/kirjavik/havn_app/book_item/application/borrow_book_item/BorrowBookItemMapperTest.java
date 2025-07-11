package com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class BorrowBookItemMapperTest {

  private DomainMetadataService domainMetadataService;
  private BorrowBookItemMapper mapper;

  @BeforeEach
  void setUp() {
    domainMetadataService = Mockito.mock(DomainMetadataService.class);
    mapper = Mappers.getMapper(BorrowBookItemMapper.class);
    mapper.domainMetadataService = domainMetadataService;
  }

  @Test
  void mapToDomain() {
    BorrowBookItemCommand command = new BorrowBookItemCommand("id", "bookId", "userId");
    Mockito.when(domainMetadataService.generateEventId()).thenReturn("eventId");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("2023-10-01T12:00:00Z");

    BookItemBorrowed actualResult = mapper.mapToDomain(command);

    assertThat(actualResult)
        .isEqualTo(
            BookItemBorrowed.builder()
                .id(command.id())
                .bookId(command.bookId())
                .userId(command.userId())
                .eventId("eventId")
                .eventType("BookItemBorrowed")
                .aggregateId(command.id())
                .aggregateType("BookItem")
                .dateTime("2023-10-01T12:00:00Z")
                .build());
  }
}
