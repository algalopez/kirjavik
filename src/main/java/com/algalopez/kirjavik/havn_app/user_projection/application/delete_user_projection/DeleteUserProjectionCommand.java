package com.algalopez.kirjavik.havn_app.user_projection.application.delete_user_projection;

import lombok.Builder;

@Builder
public record DeleteUserProjectionCommand(String id) {}
