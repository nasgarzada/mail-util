package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.core.entity.AttachmentEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Enables for operations on attachments
 * @author nasgarzada
 * @version 1.0.0
 * @see AttachmentEntity
 */
public class Attachment {
    private final Email email;
    private final List<AttachmentEntity> attachments;

    public Attachment(Email email) {
        this.attachments = new ArrayList<>();
        this.email = email;
    }

    public Attachment file(String fileName, String filePath) {
        var file = new File(filePath);
        attachments.add(new AttachmentEntity(fileName, file));
        return this;
    }

    public Attachment file(File file) {
        attachments.add(new AttachmentEntity(file.getName(), file));
        return this;
    }

    public Email build() {
        this.email.getMailDto().setAttachments(attachments);
        return this.email;
    }
}
