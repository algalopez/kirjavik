package com.algalopez.kirjavik.shared.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FakeEntityTest {

  @Test
  void justForCoverage() {
    FakeEntity fakeEntity = new FakeEntity(1L);

    assertThat(fakeEntity.getId()).isEqualTo(1L);
  }
}
