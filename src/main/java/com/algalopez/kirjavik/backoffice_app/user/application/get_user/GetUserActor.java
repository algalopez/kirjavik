package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.exception.UserNotFoundException;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class GetUserActor {

  private final GetUserMapper getUserMapper;
  private final UserViewRepositoryPort userViewRepository;

  public GetUserResponse query(GetUserQuery query) {
    UUID id = UUID.fromString(query.getId());
    UserView user = userViewRepository.findById(id);
    if (user == null) {
      throw new UserNotFoundException("User " + id + " does not exist");
    }

    return getUserMapper.mapToResponse(user);
  }
}
