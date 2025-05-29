package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateUserCommand(String id, String name, String email) {}
