package com.algalopez.kirjavik.shared.domain.port;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public interface UuidProviderPort {

  UUID getUuid();
}
