package com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteUserProjectionActorTest {
  private UserProjectionRepositoryPort userProjectionRepository;
  private DeleteUserProjectionActor deleteUserProjectionActor;

  @BeforeEach
  void setUp() {
    userProjectionRepository = Mockito.mock(UserProjectionRepositoryPort.class);
    deleteUserProjectionActor = new DeleteUserProjectionActor(userProjectionRepository);
  }

  @Test
  void command() {
    UUID id = UUID.randomUUID();
    DeleteUserProjectionCommand command =
        DeleteUserProjectionCommand.builder().id(id.toString()).build();

    deleteUserProjectionActor.command(command);

    Mockito.verify(userProjectionRepository).deleteUserProjection(id);
  }
}
