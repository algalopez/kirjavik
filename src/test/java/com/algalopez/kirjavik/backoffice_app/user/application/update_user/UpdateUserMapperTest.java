package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateUserMapperTest {

  private final UpdateUserMapper mapper = Mappers.getMapper(UpdateUserMapper.class);

  @Test
  void mapToDomain() {
    UpdateUserCommand command =
        new UpdateUserCommand("01970864-a920-7b34-9be2-1f1703a6396a", "name", "email");

    User actualUser = mapper.mapToDomain(command);

    assertThat(actualUser)
        .isEqualTo(
            User.builder()
                .id(UUID.fromString("01970864-a920-7b34-9be2-1f1703a6396a"))
                .name("name")
                .email("email")
                .build());
  }
}
