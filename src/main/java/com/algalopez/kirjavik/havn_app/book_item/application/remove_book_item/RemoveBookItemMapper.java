package com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class RemoveBookItemMapper {

  @Inject DomainMetadataService domainMetadataService;

  @Mapping(target = "eventId", expression = "java(domainMetadataService.generateEventId())")
  @Mapping(target = "dateTime", expression = "java(domainMetadataService.generateEventDateTime())")
  @Mapping(target = "eventType", constant = BookItemRemoved.EVENT_TYPE)
  @Mapping(target = "aggregateId", source = "id")
  @Mapping(target = "aggregateType", constant = BookItemRemoved.AGGREGATE_TYPE)
  public abstract BookItemRemoved mapToDomain(RemoveBookItemCommand command);
}
