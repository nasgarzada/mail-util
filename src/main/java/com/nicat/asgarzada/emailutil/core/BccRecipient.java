package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.core.entity.RecipientEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enables operation on bcc recipients.
 *
 * @author nasgarzada
 * @version 1.0.0
 * @see RecipientEntity
 * @see Recipient
 */
public class BccRecipient extends Recipient {
    private final List<RecipientEntity> recipientEntityList;

    protected BccRecipient(Email email) {
        super(email);
        this.recipientEntityList = new ArrayList<>();
    }

    @Override
    public Recipient add(String email, Boolean hasAlias) {
        this.recipientEntityList.add(new RecipientEntity(hasAlias, email));
        return this;
    }

    @Override
    public Recipient add(String email) {
        this.recipientEntityList.add(new RecipientEntity(false, email));
        return this;
    }

    @Override
    public Email add(List<String> emails) {
        this.email.getMailDto().setBcc(
                emails.stream()
                        .map(e -> new RecipientEntity(false, e))
                        .collect(Collectors.toList())
        );
        return this.email;
    }

    @Override
    public Email addAll(List<RecipientEntity> recipientDtos) {
        this.email.getMailDto().setBcc(recipientDtos);
        return this.email;
    }

    @Override
    public Email build() {
        this.email.getMailDto().setBcc(this.recipientEntityList);
        return this.email;
    }

    @Override
    public String toString() {
        return "BccRecipient{" +
                "recipientEntityList=" + recipientEntityList +
                '}';
    }
}
