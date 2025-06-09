package com.algalopez.kirjavik.havn_app.book_item.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract sealed class BookItemDomainEvent extends DomainEvent
    permits BookItemAdded, BookItemRemoved, BookItemBorrowed, BookItemReturned {}
