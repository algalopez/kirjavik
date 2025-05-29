package com.algalopez.kirjavik.backoffice_app.user.domain.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
  private UUID id;
  private String name;
  private String email;
}
