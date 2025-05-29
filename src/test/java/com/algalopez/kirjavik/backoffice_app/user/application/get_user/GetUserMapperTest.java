package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class GetUserMapperTest {

  private final GetUserMapper mapper = Mappers.getMapper(GetUserMapper.class);

  @Test
  void mapToResponse() {
    UserView user =
        UserView.builder()
            .id(UUID.fromString("01970861-aea1-7e57-ae93-478eb6b46458"))
            .name("name")
            .email("email")
            .build();

    GetUserResponse actualResponse = mapper.mapToResponse(user);

    assertThat(actualResponse)
        .isEqualTo(
            GetUserResponse.builder()
                .id("01970861-aea1-7e57-ae93-478eb6b46458")
                .name("name")
                .email("email")
                .build());
  }
}
