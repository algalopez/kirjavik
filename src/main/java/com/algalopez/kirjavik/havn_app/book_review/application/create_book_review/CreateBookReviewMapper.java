package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CreateBookReviewMapper {

  @Mapping(target = "id", ignore = true)
  BookReview mapToDomain(CreateBookReviewCommand command);
}
