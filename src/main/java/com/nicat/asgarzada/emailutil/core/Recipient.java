package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.core.entity.RecipientEntity;

import java.util.List;

/**
 * Base class for enabling operation of recipients.
 *
 * @author nasgarzada
 * @version 1.0.0
 * @see BccRecipient
 * @see CcRecipient
 */
public abstract class Recipient {
    protected final Email email;

    protected Recipient(Email emailHelper) {
        this.email = emailHelper;
    }

    public abstract Recipient add(String email, Boolean hasAlias);

    public abstract Recipient add(String email);

    public abstract Email add(List<String> emails);

    public abstract Email addAll(List<RecipientEntity> recipientDtos);

    public abstract Email build();

    @Override
    public String toString() {
        return "Recipient{" +
                "helper=" + email +
                '}';
    }
}
