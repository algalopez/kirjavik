package org.algalopez.kirjavik.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DateTimeProviderAdapterTest {

  private final DateTimeProviderAdapter dateTimeProviderAdapter = new DateTimeProviderAdapter();

  @Test
  void getDateTime() {
    LocalDateTime actualDateTime = dateTimeProviderAdapter.getDateTime();

    assertThat(actualDateTime)
        .isBetween(
            LocalDateTime.now(Clock.systemUTC()).minusMinutes(10),
            LocalDateTime.now(Clock.systemUTC()).plusMinutes(10));
  }

  @Test
  void getDate() {
    LocalDate actualDate = dateTimeProviderAdapter.getDate();

    assertThat(actualDate).isEqualTo(LocalDate.now(Clock.systemUTC()));
  }
}
