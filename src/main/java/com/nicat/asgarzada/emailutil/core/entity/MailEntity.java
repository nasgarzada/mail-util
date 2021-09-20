package com.nicat.asgarzada.emailutil.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Entity for building mails
 * @author nasgarzada
 * @version 1.0.0
 */
public class MailEntity {
    private Long id;
    private String from;
    private List<RecipientEntity> to;
    private List<RecipientEntity> cc;
    private List<RecipientEntity> bcc;
    private List<AttachmentEntity> attachments;
    private String subject;
    private String message;

    public MailEntity() {
        this.id = new Random().nextLong();
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<RecipientEntity> getTo() {
        return to;
    }

    public void setTo(List<RecipientEntity> to) {
        this.to = to;
    }

    public List<RecipientEntity> getCc() {
        return cc;
    }

    public void setCc(List<RecipientEntity> cc) {
        this.cc = cc;
    }

    public List<RecipientEntity> getBcc() {
        return bcc;
    }

    public void setBcc(List<RecipientEntity> bcc) {
        this.bcc = bcc;
    }

    public List<AttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MailDto{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to=" + to +
                ", cc=" + cc +
                ", bcc=" + bcc +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}