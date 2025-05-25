package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CreateBookMapper {

  Book mapToDomain(CreateBookCommand command);
}
