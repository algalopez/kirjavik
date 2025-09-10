package com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UpdateBookProjectionMapper {

  BookProjection mapToDomain(UpdateBookProjectionCommand command);
}
