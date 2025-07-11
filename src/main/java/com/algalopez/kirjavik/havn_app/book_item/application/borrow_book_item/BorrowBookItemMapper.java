package com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class BorrowBookItemMapper {

  @Inject DomainMetadataService domainMetadataService;

  @Mapping(target = "eventId", expression = "java(domainMetadataService.generateEventId())")
  @Mapping(target = "dateTime", expression = "java(domainMetadataService.generateEventDateTime())")
  @Mapping(target = "eventType", constant = BookItemBorrowed.EVENT_TYPE)
  @Mapping(target = "aggregateId", source = "id")
  @Mapping(target = "aggregateType", constant = BookItemBorrowed.AGGREGATE_TYPE)
  public abstract BookItemBorrowed mapToDomain(BorrowBookItemCommand command);
}
