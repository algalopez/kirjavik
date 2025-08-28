package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UpdateBookReviewMapper {

  BookReview mapToDomain(UpdateBookReviewCommand command);
}
