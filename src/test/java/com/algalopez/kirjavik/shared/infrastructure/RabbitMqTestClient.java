package com.algalopez.kirjavik.shared.infrastructure;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class RabbitMqTestClient {

  private final ObjectMapper objectMapper;
  private final ConnectionFactory factory;

  public RabbitMqTestClient(
      ObjectMapper objectMapper,
      @ConfigProperty(name = "rabbitmq-host", defaultValue = "localhost") String host,
      @ConfigProperty(name = "rabbitmq-port", defaultValue = "34606") Integer port,
      @ConfigProperty(name = "rabbitmq-username", defaultValue = "user") String username,
      @ConfigProperty(name = "rabbitmq-password", defaultValue = "pass") String password) {
    this.objectMapper = objectMapper;
    this.factory = new ConnectionFactory();
    factory.setHost(host);
    factory.setPort(port);
    factory.setUsername(username);
    factory.setPassword(password);
  }

  @SneakyThrows
  public void purgeQueue(String queueName) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {
      channel.queuePurge(queueName);
    }
  }

  @SneakyThrows
  public String consumeSingleMessage(
      String exchange, String routingKey, String queueName, long timeoutSeconds) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {

      BlockingQueue<String> receivedMessages = new ArrayBlockingQueue<>(1);

      DeliverCallback deliverCallback =
          (consumerTag, delivery) -> {
            receivedMessages.add(new String(delivery.getBody(), StandardCharsets.UTF_8));
          };

      channel.queueDeclare(queueName, false, true, true, null);
      channel.queueBind(queueName, exchange, routingKey);
      channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

      return receivedMessages.poll(timeoutSeconds, TimeUnit.SECONDS);
    }
  }

  @SneakyThrows
  public <T extends DomainEvent> T consumeSingleMessage(
      String exchange,
      String routingKey,
      String queueName,
      long timeoutSeconds,
      Class<T> targetType) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {

      BlockingQueue<String> receivedMessages = new ArrayBlockingQueue<>(1);

      DeliverCallback deliverCallback =
          (consumerTag, delivery) -> {
            receivedMessages.add(new String(delivery.getBody(), StandardCharsets.UTF_8));
          };

      channel.queueDeclare(queueName, false, true, true, null);
      channel.queueBind(queueName, exchange, routingKey);
      channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

      String message = receivedMessages.poll(timeoutSeconds, TimeUnit.SECONDS);

      return objectMapper.readValue(message, targetType);
    }
  }
}
