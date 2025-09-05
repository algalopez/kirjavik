package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewCriteria;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewViewMother;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewViewRepositoryPort;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class GetAllBookReviewActorTest {

  private BookReviewViewRepositoryPort bookReviewViewRepository;
  private GetAllBookReviewActor actor;

  @BeforeEach
  void setUp() {
    bookReviewViewRepository = Mockito.mock(BookReviewViewRepositoryPort.class);
    GetAllBookReviewMapper mapper = Mappers.getMapper(GetAllBookReviewMapper.class);
    actor = new GetAllBookReviewActor(bookReviewViewRepository, mapper);
  }

  @Test
  void query() {
    BookReviewView bookReviewView = new BookReviewViewMother().build();
    Mockito.when(
            bookReviewViewRepository.findAllReviewsByFilters(Mockito.any(BookReviewCriteria.class)))
        .thenReturn(List.of(bookReviewView));
    GetAllBookReviewQuery request =
        GetAllBookReviewQuery.builder()
            .bookId("00000000-0000-0000-0000-000000000001")
            .userId("00000000-0000-0000-0000-000000000002")
            .build();

    GetAllBookReviewResponse actualResponse = actor.query(request);

    assertThat(actualResponse)
        .isEqualTo(
            GetAllBookReviewResponse.builder()
                .bookReviews(
                    List.of(
                        GetAllBookReviewResponse.BookReview.builder()
                            .id(bookReviewView.id())
                            .bookId(bookReviewView.bookId().toString())
                            .bookTitle(bookReviewView.bookTitle())
                            .userId(bookReviewView.userId().toString())
                            .rating(bookReviewView.rating())
                            .comment(bookReviewView.comment())
                            .build()))
                .build());
  }
}
