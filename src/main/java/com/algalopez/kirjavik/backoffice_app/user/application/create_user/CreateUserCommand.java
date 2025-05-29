package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import lombok.Builder;

@Builder
public record CreateUserCommand(String id, String name, String email) {}
