package org.algalopez.kirjavik.shared.domain.port;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimeProviderPort {

    LocalDateTime getDateTime();

    LocalDate getDate();
}
