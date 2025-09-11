package com.algalopez.kirjavik.havn_app.user_projection.domain.port;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import java.util.UUID;

public interface UserProjectionRepositoryPort {
  UserProjection findUserProjectionById(UUID id);

  void createUserProjection(UserProjection userProjection);

  void updateUserProjection(UserProjection userProjection);

  void deleteUserProjection(UUID id);
}
