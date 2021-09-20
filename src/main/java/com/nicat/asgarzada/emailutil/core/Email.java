package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.annotation.Bind;
import com.nicat.asgarzada.emailutil.core.entity.MailEntity;
import com.nicat.asgarzada.emailutil.core.entity.RecipientEntity;
import com.nicat.asgarzada.emailutil.sender.Sender;
import com.nicat.asgarzada.emailutil.util.TemplateUtil;

import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for building {@link MailEntity} with declarative helper methods.
 * Supports:
 * <li> html or plain text rendering;</li>
 * <li> sending multiple attachments;</li>
 * <li> key value binding with {@link Bind} annotation;</li>
 * <li> customizable channels for sending mail. For details see: {@link Sender}</li>
 *
 * @author nasgarzada
 * @version 1.0.0
 * @since 2021-09-17
 */
public final class Email {
    private boolean withRenderer;
    private Renderer renderer;
    private final MailEntity mailEntity;

    private Email() {
        this.withRenderer = false;
        this.mailEntity = new MailEntity();
    }

    /**
     *
     * @return Mail entity
     */
    MailEntity getMailDto() {
        return this.mailEntity;
    }

    /**
     * base method for starting to build mail and sending it
     * @return email {@link Email}
     */
    public static Email builder() {
        return new Email();
    }

    /**
     * Enables to add file for rendering with template engine
     * @return renderer {@link Renderer}
     */
    public Renderer withRenderer() {
        this.withRenderer = true;
        this.renderer = new Renderer(this);
        return this.renderer;
    }

    /**
     * Enables to add attachment files
     * @return attachment {@link Attachment}
     */
    public Attachment withAttachment() {
        return new Attachment(this);
    }

    /**
     * Sets mail subject
     * @param subject mail subject
     * @return email {@link Email}
     */
    public Email subject(String subject) {
        this.mailEntity.setSubject(subject);
        return this;
    }

    /**
     * Sets mail subject with params for replacing.
     * @param subject mail subject
     * @param params additional params for replacing in subject text
     * @return email {@link Email}
     */
    public Email subject(String subject, String... params) {
        this.mailEntity.setSubject(String.format(subject, params));
        return this;
    }

    /**
     * Sets mail originator address
     * @param email mail address of originator
     * @return email {@link Email}
     */
    public Email from(String email) {
        this.mailEntity.setFrom(email);
        return this;
    }

    /**
     * Sets to recipient
     * @param to email of recipient
     * @param hasAlias if has alias name for mail address
     * @return email {@link Email}
     */
    public Email to(String to, Boolean hasAlias) {
        this.mailEntity.setTo(List.of(new RecipientEntity(hasAlias, to)));
        return this;
    }

    /**
     * Enables to set cc recipients
     * @return recipient {@link Recipient}
     */
    public Recipient cc() {
        return new CcRecipient(this);
    }

    /**
     * Enables to set bcc recipients
     * @return recipient {@link Recipient}
     */
    public Recipient bcc() {
        return new BccRecipient(this);
    }

    /**
     * With lambdas can be implemented custom channel for sending
     * @param consumer
     */
    public void sendWithOwnChannel(Consumer<MailEntity> consumer) {
        prepareTemplate();
        consumer.accept(this.mailEntity);
    }

    /**
     * After building email this method enables for sending mail.
     * @param sender channel for sending mail
     * @param <T>
     * @return
     */
    public <T> T send(Sender<T> sender) {
        prepareTemplate();
        return sender.send(this.mailEntity);
    }

    /**
     * Method for preparing template with renderer or not.
     */
    private void prepareTemplate() {
        var template = !this.withRenderer ?
                this.mailEntity.getMessage() :
                TemplateUtil.renderHtml(this.renderer.getTemplatePath(), this.renderer.getBindings());
        this.mailEntity.setMessage(template);
    }

    @Override
    public String toString() {
        return "Email{" +
                "withRenderer=" + withRenderer +
                ", renderer=" + renderer +
                ", mailDto=" + mailEntity +
                '}';
    }
}
