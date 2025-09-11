package com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteUserProjectionActor {

  private final UserProjectionRepositoryPort userProjectionRepository;

  public void command(DeleteUserProjectionCommand command) {
    UUID id = UUID.fromString(command.id());
    userProjectionRepository.deleteUserProjection(id);
  }
}
