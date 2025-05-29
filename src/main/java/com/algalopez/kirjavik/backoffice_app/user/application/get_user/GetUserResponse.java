package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import lombok.Builder;

@Builder
public record GetUserResponse(String id, String name, String email) {}
