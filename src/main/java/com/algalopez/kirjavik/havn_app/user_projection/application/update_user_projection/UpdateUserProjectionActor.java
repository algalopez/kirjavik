package com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.exception.UserProjectionNotFoundException;
import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateUserProjectionActor {

  private final UpdateUserProjectionMapper updateUserProjectionMapper;
  private final UserProjectionRepositoryPort userProjectionRepository;

  public void command(UpdateUserProjectionCommand command) {
    UUID id = UUID.fromString(command.id());
    ensureUserExists(id);
    UserProjection updatedUserProjection = updateUserProjectionMapper.mapToDomain(command);
    userProjectionRepository.updateUserProjection(updatedUserProjection);
  }

  private void ensureUserExists(UUID id) {
    UserProjection userProjection = userProjectionRepository.findUserProjectionById(id);
    if (userProjection == null) {
      throw new UserProjectionNotFoundException("User " + id + " does not exist");
    }
  }
}
