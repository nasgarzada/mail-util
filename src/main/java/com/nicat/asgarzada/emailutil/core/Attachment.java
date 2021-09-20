package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.core.entity.AttachmentEntity;
import jakarta.activation.FileTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Enables for operations on attachments
 *
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

    public Attachment file(String filePath) {
        var file = new File(filePath);
        try {
            var fileIs = new FileInputStream(file);
            attachments.add(new AttachmentEntity(file.getName(), fileIs, getMimeType(file)));
        } catch (FileNotFoundException e) {
            throw new com.nicat.asgarzada.emailutil.exception.FileNotFoundException("file not found: ", e);
        }
        return this;
    }

    public Attachment file(InputStream is, String fileName, String mimeType) {
        attachments.add(new AttachmentEntity(fileName, is, mimeType));
        return this;
    }

    public Attachment file(File file) {
        try {
            attachments.add(new AttachmentEntity(file.getName(), new FileInputStream(file), getMimeType(file)));
        } catch (FileNotFoundException e) {
            throw new com.nicat.asgarzada.emailutil.exception.FileNotFoundException("file not found: ", e);
        }
        return this;
    }

    public Email build() {
        this.email.getMailDto().setAttachments(attachments);
        return this.email;
    }

    private String getMimeType(File file) {
        return FileTypeMap.getDefaultFileTypeMap().getContentType(file);
    }
}
