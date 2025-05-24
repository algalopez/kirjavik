package org.algalopez.kirjavik.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UuidProviderAdapterTest {

  private final UuidProviderAdapter uuidProviderAdapter = new UuidProviderAdapter();

  @Test
  void getUuid() {
    UUID actualUuid = uuidProviderAdapter.getUuid();

    assertThat(actualUuid).isNotNull();
    String UuidString = actualUuid.toString();
    assertThatNoException().isThrownBy(() -> UUID.fromString(UuidString));
  }
}
