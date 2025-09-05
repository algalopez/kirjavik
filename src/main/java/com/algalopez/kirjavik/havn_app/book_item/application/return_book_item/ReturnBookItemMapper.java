package com.algalopez.kirjavik.havn_app.book_item.application.return_book_item;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class ReturnBookItemMapper {

  // Can't use construction injection due to mapstruct limitations
  @SuppressWarnings("java:S6813")
  @Inject
  DomainMetadataService domainMetadataService;

  @Mapping(target = "eventId", expression = "java(domainMetadataService.generateEventId())")
  @Mapping(target = "dateTime", expression = "java(domainMetadataService.generateEventDateTime())")
  @Mapping(target = "eventType", constant = BookItemReturned.EVENT_TYPE)
  @Mapping(target = "aggregateId", source = "id")
  @Mapping(target = "aggregateType", constant = BookItemReturned.AGGREGATE_TYPE)
  public abstract BookItemReturned mapToDomain(ReturnBookItemCommand command);
}
