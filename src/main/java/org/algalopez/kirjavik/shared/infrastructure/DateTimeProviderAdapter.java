package org.algalopez.kirjavik.shared.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;

@ApplicationScoped
public class DateTimeProviderAdapter implements DateTimeProviderPort {
  @Override
  public LocalDateTime getDateTime() {
    return LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.SECONDS);
  }

  @Override
  public LocalDate getDate() {
    return LocalDate.now(Clock.systemUTC());
  }
}
