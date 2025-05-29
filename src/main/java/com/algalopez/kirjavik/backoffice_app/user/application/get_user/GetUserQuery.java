package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserQuery {
  private String id;
}
