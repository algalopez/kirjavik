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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EventBusAdapterIntegrationTest {

  private static final String QUEUE_NAME = "backoffice-test-queue";
  private static final String EXCHANGE_NAME = "kirjavik.backoffice.domain-events";

  @Inject EventBusAdapter eventBusAdapter;
  @Inject RabbitMqTestClient rabbitMqTestClient;

  @BeforeEach
  void setUp() {
    rabbitMqTestClient.prepareQueue(EXCHANGE_NAME, "#", QUEUE_NAME);
  }

  @AfterEach
  void tearDown() {
    rabbitMqTestClient.deleteQueue(QUEUE_NAME);
  }

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
        rabbitMqTestClient.consumeSingleMessage(QUEUE_NAME, 10, SampleEvent.class);
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
