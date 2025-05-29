package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.backoffice_app.user.domain.exception.UserNotFoundException;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserViewMother;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class GetUserActorTest {

  private UserViewRepositoryPort userViewRepository;
  private GetUserActor getUserActor;

  @BeforeEach
  void setUp() {
    GetUserMapper getUserMapper = Mappers.getMapper(GetUserMapper.class);
    userViewRepository = Mockito.mock(UserViewRepositoryPort.class);
    getUserActor = new GetUserActor(getUserMapper, userViewRepository);
  }

  @Test
  void query() {
    UserView user = new UserViewMother().build();
    GetUserQuery query = new GetUserQuery(user.id().toString());
    Mockito.when(userViewRepository.findById(Mockito.any(UUID.class))).thenReturn(user);

    GetUserResponse actualResponse = getUserActor.query(query);

    assertThat(actualResponse)
        .isEqualTo(
            GetUserResponse.builder()
                .id(user.id().toString())
                .name(user.name())
                .email(user.email())
                .build());
  }

  @Test
  void query_whenUserNotFound() {
    UserView user = new UserViewMother().build();
    GetUserQuery query = new GetUserQuery(user.id().toString());
    Mockito.when(userViewRepository.findById(Mockito.any(UUID.class))).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> getUserActor.query(query));
  }
}
