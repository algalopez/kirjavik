package com.algalopez.kirjavik.shared.infrastructure;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class RabbitMqTestClient {

  /*
  We are creating the queue in one connection, consuming the events in another and deleting the queue in a third one
  This is to avoid writing the whole test inside one connection or to avoid reusing the same queue for different tests
  This might change in the future if we find a better way to do it but for now it explains the following constants
   */
  private static final boolean NOT_DURABLE = false;
  private static final boolean NOT_EXCLUSIVE = false;
  private static final boolean NOT_AUTODELETE = false;

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
  public void prepareQueue(String exchange, String routingKey, String queueName) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {
      channel.queueDeclare(queueName, NOT_DURABLE, NOT_EXCLUSIVE, NOT_AUTODELETE, null);
      channel.queueBind(queueName, exchange, routingKey);
    }
  }

  @SneakyThrows
  public void deleteQueue(String queueName) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {
      channel.queueDelete(queueName);
    }
  }

  @SneakyThrows
  public <T extends DomainEvent> T consumeSingleMessage(
      String queueName, long timeoutSeconds, Class<T> targetType) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {

      BlockingQueue<String> receivedMessages = new ArrayBlockingQueue<>(1);

      DeliverCallback deliverCallback =
          (consumerTag, delivery) -> {
            receivedMessages.add(new String(delivery.getBody(), StandardCharsets.UTF_8));
          };

      channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

      String message = receivedMessages.poll(timeoutSeconds, TimeUnit.SECONDS);

      return objectMapper.readValue(message, targetType);
    }
  }

  @SneakyThrows
  public <T extends DomainEvent> void publishMessage(
      String exchangeName, String routingKey, byte[] jsonBytes) {
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {

      AMQP.BasicProperties props =
          new AMQP.BasicProperties.Builder().contentType("application/json").build();

      channel.basicPublish(exchangeName, routingKey, props, jsonBytes);
    }
  }
}
