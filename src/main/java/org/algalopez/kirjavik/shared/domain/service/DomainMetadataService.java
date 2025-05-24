package org.algalopez.kirjavik.shared.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import org.algalopez.kirjavik.shared.domain.port.UuidProviderPort;

@RequiredArgsConstructor
@ApplicationScoped
public class DomainMetadataService {

  private final UuidProviderPort uuidProviderPort;
  private final DateTimeProviderPort dateTimeProviderPort;

  public String generateEventId() {
    return uuidProviderPort.getUuid().toString();
  }

  public String generateEventDateTime() {
    return dateTimeProviderPort.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
