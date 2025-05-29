package com.algalopez.kirjavik.backoffice_app.user.domain.view;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserView(UUID id, String name, String email) {}
