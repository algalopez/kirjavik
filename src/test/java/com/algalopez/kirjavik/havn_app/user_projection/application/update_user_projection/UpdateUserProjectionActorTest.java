package com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.havn_app.user_projection.domain.exception.UserProjectionNotFoundException;
import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjectionMother;
import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class UpdateUserProjectionActorTest {

  private UserProjectionRepositoryPort userProjectionRepository;
  private UpdateUserProjectionActor updateUserProjectionActor;

  @BeforeEach
  void setUp() {
    UpdateUserProjectionMapper updateUserProjectionMapper =
        Mappers.getMapper(UpdateUserProjectionMapper.class);
    userProjectionRepository = Mockito.mock(UserProjectionRepositoryPort.class);
    updateUserProjectionActor =
        new UpdateUserProjectionActor(updateUserProjectionMapper, userProjectionRepository);
  }

  @Test
  void command() {
    UpdateUserProjectionCommand command = buildUpdateUserProjectionCommand();
    Mockito.when(userProjectionRepository.findUserProjectionById(Mockito.any(UUID.class)))
        .thenReturn(new UserProjectionMother().build());

    updateUserProjectionActor.command(command);

    Mockito.verify(userProjectionRepository)
        .updateUserProjection(
            UserProjection.builder()
                .id(UUID.fromString(command.id()))
                .name(command.name())
                .email(command.email())
                .build());
  }

  @Test
  void command_whenUserDoesNotExist() {
    UpdateUserProjectionCommand command = buildUpdateUserProjectionCommand();

    assertThatExceptionOfType(UserProjectionNotFoundException.class)
        .isThrownBy(() -> updateUserProjectionActor.command(command));

    Mockito.verify(userProjectionRepository, Mockito.never())
        .updateUserProjection(Mockito.any(UserProjection.class));
  }

  private static UpdateUserProjectionCommand buildUpdateUserProjectionCommand() {
    return UpdateUserProjectionCommand.builder()
        .id(UUID.randomUUID().toString())
        .name("name")
        .email("email")
        .build();
  }
}
