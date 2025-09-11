package com.algalopez.kirjavik.havn_app.user_projection.domain.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProjection {
  private UUID id;
  private String name;
  private String email;
}
