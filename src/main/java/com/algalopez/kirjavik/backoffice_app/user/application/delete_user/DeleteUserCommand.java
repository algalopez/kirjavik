package com.algalopez.kirjavik.backoffice_app.user.application.delete_user;

import lombok.Builder;

@Builder
public record DeleteUserCommand(String id) {}
