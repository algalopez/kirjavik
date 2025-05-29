package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserViewRepositoryAdapter implements UserViewRepositoryPort {
    @Override
    public UserView findById(UUID id) {
        return null;
    }
}
