package com.algalopez.kirjavik.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

class EventBusAdapterTest {

  private final EventBusAdapter eventBusAdapter = new EventBusAdapter();

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
  }

  @ToString(callSuper = true)
  @Getter
  @SuperBuilder
  private static class SampleEvent extends DomainEvent {

    private final String payload;
  }
}
