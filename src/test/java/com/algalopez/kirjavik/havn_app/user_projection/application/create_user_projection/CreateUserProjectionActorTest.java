package com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CreateUserProjectionActorTest {

  private UserProjectionRepositoryPort userProjectionRepository;
  private CreateUserProjectionActor createUserProjectionActor;

  @BeforeEach
  void setUp() {
    userProjectionRepository = Mockito.mock(UserProjectionRepositoryPort.class);
    CreateUserProjectionMapper createUserProjectionMapper =
        Mappers.getMapper(CreateUserProjectionMapper.class);
    createUserProjectionActor =
        new CreateUserProjectionActor(createUserProjectionMapper, userProjectionRepository);
  }

  @Test
  void command() {
    CreateUserProjectionCommand createUserProjectionCommand = buildCreateUserProjectionCommand();

    createUserProjectionActor.command(createUserProjectionCommand);

    Mockito.verify(userProjectionRepository)
        .createUserProjection(
            UserProjection.builder()
                .id(UUID.fromString(createUserProjectionCommand.id()))
                .name(createUserProjectionCommand.name())
                .email(createUserProjectionCommand.email())
                .build());
  }

  private static CreateUserProjectionCommand buildCreateUserProjectionCommand() {
    return CreateUserProjectionCommand.builder()
        .id(UUID.randomUUID().toString())
        .name("name")
        .email("email")
        .build();
  }
}
