package com.algalopez.kirjavik.havn_app.user_projection.api;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class UserCreatedEvent extends DomainEvent {

  public static final String EVENT_TYPE = "UserCreated";
  public static final String AGGREGATE_TYPE = "User";

  private final String id;
  private final String name;
  private final String email;

  @JsonCreator
  public UserCreatedEvent(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("email") String email,
      @JsonProperty("eventId") String eventId,
      @JsonProperty("eventType") String eventType,
      @JsonProperty("aggregateId") String aggregateId,
      @JsonProperty("aggregateType") String aggregateType,
      @JsonProperty("dateTime") String dateTime) {
    super(eventId, eventType, aggregateId, aggregateType, dateTime);
    this.id = id;
    this.name = name;
    this.email = email;
  }
}
