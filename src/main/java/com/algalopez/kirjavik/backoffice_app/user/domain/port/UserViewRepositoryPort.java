package com.algalopez.kirjavik.backoffice_app.user.domain.port;

import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import java.util.UUID;

public interface UserViewRepositoryPort {
  UserView findById(UUID id);
}
