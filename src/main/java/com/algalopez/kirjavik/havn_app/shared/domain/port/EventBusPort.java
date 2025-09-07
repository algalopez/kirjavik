package com.algalopez.kirjavik.havn_app.shared.domain.port;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;

public interface EventBusPort {
    void publish(DomainEvent event);
}
