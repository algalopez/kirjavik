package com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateUserProjectionActor {

  private final CreateUserProjectionMapper createUserProjectionMapper;
  private final UserProjectionRepositoryPort userProjectionRepository;

  public void command(CreateUserProjectionCommand command) {
    UserProjection userProjection = createUserProjectionMapper.mapToDomain(command);
    userProjectionRepository.createUserProjection(userProjection);
  }
}
