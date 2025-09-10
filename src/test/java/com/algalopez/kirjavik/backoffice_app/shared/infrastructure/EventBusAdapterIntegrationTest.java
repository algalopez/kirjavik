package com.algalopez.kirjavik.backoffice_app.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.algalopez.kirjavik.shared.infrastructure.RabbitMqTestClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EventBusAdapterIntegrationTest {

  @Inject EventBusAdapter eventBusAdapter;
  @Inject RabbitMqTestClient rabbitMqTestClient;

  @Test
  void publish() {
    SampleEvent sampleEvent =
        SampleEvent.builder()
            .eventId("eventId")
            .eventType("eventType")
            .aggregateId("aggregateId")
            .aggregateType("aggregateType")
            .dateTime("dateTime")
            .payload("payload")
            .build();

    assertThatNoException().isThrownBy(() -> eventBusAdapter.publish(sampleEvent));

    SampleEvent publishedEvent =
        rabbitMqTestClient.consumeSingleMessage(
            "kirjavik.backoffice.domain-events",
            "#",
            "backoffice-test-queue",
            2,
            SampleEvent.class);
    assertThat(publishedEvent).isEqualTo(sampleEvent);
  }

  @ToString(callSuper = true)
  @Getter
  @SuperBuilder
  @Jacksonized
  private static class SampleEvent extends DomainEvent {

    private final String payload;
  }
}
