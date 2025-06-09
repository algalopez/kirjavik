package com.algalopez.kirjavik.havn_app.book_item.infrastructure.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "eventstore")
public interface EventStoreConfig {

  String host();

  int port();

  boolean tls();

  String username();

  String password();
}
