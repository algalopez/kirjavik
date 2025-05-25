package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface GetBookMapper {

  GetBookResponse mapToResponse(BookView book);
}
