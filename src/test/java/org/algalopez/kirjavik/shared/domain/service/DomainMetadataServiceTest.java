package org.algalopez.kirjavik.shared.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import org.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DomainMetadataServiceTest {

  private UuidProviderPort uuidProviderPort;
  private DateTimeProviderPort dateTimeProviderPort;
  private DomainMetadataService domainMetadataService;

  @BeforeEach
  void setUp() {
    uuidProviderPort = Mockito.mock(UuidProviderPort.class);
    dateTimeProviderPort = Mockito.mock(DateTimeProviderPort.class);
    domainMetadataService = new DomainMetadataService(uuidProviderPort, dateTimeProviderPort);
  }

  @Test
  void generateEventId() {
    UUID uuid = UUID.randomUUID();
    Mockito.when(uuidProviderPort.getUuid()).thenReturn(uuid);

    String actualEventId = domainMetadataService.generateEventId();

    assertThat(actualEventId).isEqualTo(uuid.toString());
  }

  @Test
  void generateEventDateTime() {
    Mockito.when(dateTimeProviderPort.getDateTime())
        .thenReturn(LocalDateTime.of(2025, 1, 2, 3, 4, 5));

    String actualEventDateTime = domainMetadataService.generateEventDateTime();

    assertThat(actualEventDateTime).isEqualTo("2025-01-02T03:04:05");
  }
}
