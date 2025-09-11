package com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateUserProjectionMapperTest {

  private final CreateUserProjectionMapper mapper =
      Mappers.getMapper(CreateUserProjectionMapper.class);

  @Test
  void mapToDomain() {
    var command =
        new CreateUserProjectionCommand("01970853-2532-73e2-b36d-2ee42dc19a26", "name", "email");

    UserProjection actualUserProjection = mapper.mapToDomain(command);

    assertThat(actualUserProjection)
        .isEqualTo(
            UserProjection.builder()
                .id(UUID.fromString("01970853-2532-73e2-b36d-2ee42dc19a26"))
                .name("name")
                .email("email")
                .build());
  }
}
