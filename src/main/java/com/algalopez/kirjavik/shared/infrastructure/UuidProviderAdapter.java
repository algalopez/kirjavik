package com.algalopez.kirjavik.shared.infrastructure;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;

@ApplicationScoped
public class UuidProviderAdapter implements UuidProviderPort {

  @Override
  public UUID getUuid() {
    return UUID.randomUUID();
  }
}
