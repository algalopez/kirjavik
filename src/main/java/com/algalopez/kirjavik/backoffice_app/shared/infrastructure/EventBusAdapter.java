package com.algalopez.kirjavik.backoffice_app.shared.infrastructure;

import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@Slf4j
@ApplicationScoped
public class EventBusAdapter implements EventBusPort {

  private static final String PROJECT_PREFIX = "kirjavik.backoffice";

  private final Emitter<DomainEvent> emitter;

  public EventBusAdapter(@Channel("backoffice-domain-events") Emitter<DomainEvent> channel) {
    this.emitter = channel;
  }

  @Override
  public void publish(DomainEvent event) {
    log.info("Publishing event: {}", event);

    String routingKey =
        String.join(".", PROJECT_PREFIX, event.getAggregateType(), event.getEventType());
    OutgoingRabbitMQMetadata metadata =
        OutgoingRabbitMQMetadata.builder().withRoutingKey(routingKey).build();
    Message<DomainEvent> message = Message.of(event).addMetadata(metadata);

    emitter.send(message);
  }
}
