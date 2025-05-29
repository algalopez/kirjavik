package com.algalopez.kirjavik.backoffice_app.user.domain.view;

import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Setter
@Accessors(fluent = true)
public class UserViewMother {
  private UUID id;
  private String name;
  private String email;

  public UserViewMother() {
    var faker = new Faker();
    this.id = UUID.fromString(faker.internet().uuidv7());
    this.name = faker.name().name();
    this.email = faker.internet().emailAddress();
  }

  public UserView build() {
    return UserView.builder().id(id).name(name).email(email).build();
  }
}
