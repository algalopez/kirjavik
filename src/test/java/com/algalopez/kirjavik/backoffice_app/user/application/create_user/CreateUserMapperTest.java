package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateUserMapperTest {

  private final CreateUserMapper mapper = Mappers.getMapper(CreateUserMapper.class);

  @Test
  void mapToDomain() {
    var command = new CreateUserCommand("01970853-2532-73e2-b36d-2ee42dc19a26", "name", "email");

    User actualUser = mapper.mapToDomain(command);

    assertThat(actualUser)
        .isEqualTo(
            User.builder()
                .id(UUID.fromString("01970853-2532-73e2-b36d-2ee42dc19a26"))
                .name("name")
                .email("email")
                .build());
  }
}
