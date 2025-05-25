package com.algalopez.kirjavik.backoffice_app.book.domain.view;

import java.util.List;
import lombok.Builder;

@Builder
public record BookViewSpec(List<Filter> filters) {

  @Builder
  public record Filter(String field, Operator operator, String value) {}

  public enum Operator {
    EQUALS,
    STARTS_WITH,
    GREATER_THAN
  }
}
