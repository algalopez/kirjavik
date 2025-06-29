package com.algalopez.kirjavik.havn_app.book_item.infrastructure.model;

import lombok.Builder;

@Builder
public record LastEventMetadata(String uuid, Long revision) {}
