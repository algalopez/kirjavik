package com.algalopez.kirjavik.havn_app.book_item.application.add_book_item;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class AddBookItemMapper {

  @Inject DomainMetadataService domainMetadataService;

  @Mapping(target = "eventId", expression = "java(domainMetadataService.generateEventId())")
  @Mapping(target = "dateTime", expression = "java(domainMetadataService.generateEventDateTime())")
  @Mapping(target = "eventType", constant = BookItemAdded.EVENT_TYPE)
  @Mapping(target = "aggregateId", source = "id")
  @Mapping(target = "aggregateType", constant = BookItemAdded.AGGREGATE_TYPE)
  public abstract BookItemAdded mapToDomain(AddBookItemCommand command);
}
