package com.leozhi.monote.bean;

import android.net.Uri;

/**
 * @author leozhi
 * @date 20-9-16
 */
public class FileBean {
    private Uri fileUri;
    private String filePath;
    private String fileName;
    private String fileSize;
    private long fileDate;

    public FileBean(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public long getFileDate() {
        return fileDate;
    }

    public void setFileDate(long fileDate) {
        this.fileDate = fileDate;
    }
}
