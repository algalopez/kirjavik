package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UpdateBookMapper {

  Book mapToDomain(UpdateBookCommand command);
}
