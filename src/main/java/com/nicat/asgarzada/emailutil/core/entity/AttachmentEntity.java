package com.nicat.asgarzada.emailutil.core.entity;

import java.io.File;

/**
 * For saving attachments info
 * @author nasgarzada
 * @version 1.0.0
 */
public class AttachmentEntity {
    private String fileName;
    private File file;

    public AttachmentEntity() {
    }

    public AttachmentEntity(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
