package org.algalopez.kirjavik.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThatNoException;

import org.algalopez.kirjavik.shared.domain.model.DomainEvent;
import org.junit.jupiter.api.Test;

class EventBusAdapterTest {

  private final EventBusAdapter eventBusAdapter = new EventBusAdapter();

  @Test
  void publish() {
    SampleEvent sampleEvent =
        new SampleEvent("eventId", "eventType", "aggregateId", "aggregateType", "dateTime");

    assertThatNoException().isThrownBy(() -> eventBusAdapter.publish(sampleEvent));
  }

  private static class SampleEvent extends DomainEvent {

    public SampleEvent(
        String eventId,
        String eventType,
        String aggregateId,
        String aggregateType,
        String dateTime) {
      super(eventId, eventType, aggregateId, aggregateType, dateTime);
    }
  }
}
