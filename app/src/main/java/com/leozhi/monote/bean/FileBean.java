package com.leozhi.monote.bean;

import android.net.Uri;

/**
 * @author leozhi
 * @date 20-9-16
 */
public class FileBean {
    private Uri fileUri;
    private int depth;
    private String fileName;
    private String fileSize;
    private long fileDate;

    public FileBean(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public long getFileDate() {
        return fileDate;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileDate(long fileDate) {
        this.fileDate = fileDate;
    }
}
