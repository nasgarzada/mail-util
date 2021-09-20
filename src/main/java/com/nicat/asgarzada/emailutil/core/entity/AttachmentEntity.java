package com.nicat.asgarzada.emailutil.core.entity;

import java.io.InputStream;

/**
 * For saving attachments info
 *
 * @author nasgarzada
 * @version 1.0.0
 */
public class AttachmentEntity {
    private String fileName;
    private String mimeType;
    private InputStream file;

    public AttachmentEntity() {
    }

    public AttachmentEntity(String fileName, InputStream file, String mimeType) {
        this.fileName = fileName;
        this.file = file;
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
