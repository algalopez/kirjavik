package com.algalopez.kirjavik.havn_app.book_review.application.get_book_review;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewViewMother;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewViewRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class GetBookReviewActorTest {

  private BookReviewViewRepositoryPort bookReviewViewRepository;
  private GetBookReviewActor actor;

  @BeforeEach
  void setUp() {
    bookReviewViewRepository = Mockito.mock(BookReviewViewRepositoryPort.class);
    GetBookReviewMapper mapper = Mappers.getMapper(GetBookReviewMapper.class);
    actor = new GetBookReviewActor(bookReviewViewRepository, mapper);
  }

  @Test
  void query() {
    BookReviewView bookReview = new BookReviewViewMother().build();
    Mockito.when(
            bookReviewViewRepository.findBookReviewByBookIdAndUserId(
                Mockito.any(UUID.class), Mockito.any(UUID.class)))
        .thenReturn(bookReview);
    GetBookReviewQuery request =
        GetBookReviewQuery.builder()
            .bookId("00000000-0000-0000-0000-000000000001")
            .userId("00000000-0000-0000-0000-000000000002")
            .build();

    GetBookReviewResponse actualResponse = actor.query(request);

    assertThat(actualResponse)
        .isEqualTo(
            GetBookReviewResponse.builder()
                .id(bookReview.id())
                .bookId(bookReview.bookId().toString())
                .bookTitle(bookReview.bookTitle())
                .userId(bookReview.userId().toString())
                .rating(bookReview.rating())
                .comment(bookReview.comment())
                .build());
  }
}
