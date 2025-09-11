package com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateUserProjectionMapperTest {

  private final UpdateUserProjectionMapper mapper =
      Mappers.getMapper(UpdateUserProjectionMapper.class);

  @Test
  void mapToDomain() {
    UpdateUserProjectionCommand command =
        new UpdateUserProjectionCommand("01970864-a920-7b34-9be2-1f1703a6396a", "name", "email");

    UserProjection actualUserProjection = mapper.mapToDomain(command);

    assertThat(actualUserProjection)
        .isEqualTo(
            UserProjection.builder()
                .id(UUID.fromString("01970864-a920-7b34-9be2-1f1703a6396a"))
                .name("name")
                .email("email")
                .build());
  }
}
