package com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection;

import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateUserProjectionCommand(String id, String name, String email) {}
