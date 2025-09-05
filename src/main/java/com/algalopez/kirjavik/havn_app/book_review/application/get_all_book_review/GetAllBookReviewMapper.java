package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface GetAllBookReviewMapper {

  GetAllBookReviewResponse.BookReview toResponse(BookReviewView bookReviewView);
}
