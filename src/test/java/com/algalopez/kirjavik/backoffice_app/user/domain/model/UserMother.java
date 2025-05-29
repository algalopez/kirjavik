package com.algalopez.kirjavik.backoffice_app.user.domain.model;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

import java.util.UUID;

@Setter
@Accessors(fluent = true)
public class UserMother {
  private UUID id;
  private String name;
  private String email;

  public UserMother() {
    var faker = new Faker();
    this.id = UUID.fromString(faker.internet().uuidv7());
    this.name = faker.name().name();
    this.email = faker.internet().emailAddress();
  }

  public User build() {
    return User.builder().id(id).name(name).email(email).build();
  }
}
