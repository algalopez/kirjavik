package com.algalopez.kirjavik.havn_app.book_item.infrastructure.config;

import io.kurrent.dbclient.KurrentDBClient;
import io.kurrent.dbclient.KurrentDBClientSettings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class EventStoreClientProducer {

  private final EventStoreConfig config;

  @Produces
  @Singleton
  public KurrentDBClient eventStoreDBClient() {
    log.info(
        "Connecting to EventStoreDB at host: {}, port: {}, tls: {}, username: {}, password: {}",
        config.host(),
        config.port(),
        config.tls(),
        config.username(),
        config.password());

    KurrentDBClientSettings settings =
        KurrentDBClientSettings.builder()
            .addHost(new InetSocketAddress(config.host(), config.port()))
            .tls(config.tls())
            .defaultCredentials(config.username(), config.password())
            .maxDiscoverAttempts(50)
            .defaultDeadline(10_000)
            .tlsVerifyCert(false)
            .buildConnectionSettings();

    return KurrentDBClient.create(settings);
  }
}
