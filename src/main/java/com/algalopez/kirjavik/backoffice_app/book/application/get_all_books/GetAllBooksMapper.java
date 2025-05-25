package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface GetAllBooksMapper {

  BookViewSpec mapToSpec(GetAllBooksRequest query);

  GetAllBooksResponse.BookDto mapToResponse(BookView book);
}
