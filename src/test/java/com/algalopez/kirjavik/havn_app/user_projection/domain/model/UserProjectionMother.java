package com.algalopez.kirjavik.havn_app.user_projection.domain.model;

import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Setter
@Accessors(fluent = true)
public class UserProjectionMother {
  private UUID id;
  private String name;
  private String email;

  public UserProjectionMother() {
    var faker = new Faker();
    this.id = UUID.fromString(faker.internet().uuidv7());
    this.name = faker.name().name();
    this.email = faker.internet().emailAddress();
  }

  public UserProjection build() {
    return UserProjection.builder().id(id).name(name).email(email).build();
  }
}
