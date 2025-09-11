package com.algalopez.kirjavik.havn_app.user_projection.application.create_user_projection;

import lombok.Builder;

@Builder
public record CreateUserProjectionCommand(String id, String name, String email) {}
