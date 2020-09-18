package com.leozhi.monote.util;

import android.content.Context;
import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import com.leozhi.monote.bean.FileBean;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author leozhi
 * @date 20-9-17
 */
public class FileUtils {
    /**
     * 获取路径
     */
    public static String[] getPathByUri(Uri uri) {
        String path;
        if (Objects.requireNonNull(uri.getLastPathSegment()).startsWith("home:")) {
            path = uri.getLastPathSegment().substring(5) + "Documents";
        } else {
            path = uri.getLastPathSegment().split(":")[1];
        }
        return path.split(File.separator);
    }

    /**
     * 获取文件或目录相较于所选根目录的深度
     */
    public static int getFileOrDirectoryDepth(Uri uri) {
        return getPathByUri(uri).length;
    }

    /**
     * 获取文件或目录的名称
     */
    public static String getFileOrDirectoryName(Uri uri) {
        return getPathByUri(uri)[getFileOrDirectoryDepth(uri) - 1];
    }

    /**
     * 获取目录下的文件及文件夹
     */
    public static List<FileBean> getFileOrDirectoryList(Uri uri, Context context) {
        List<FileBean> fileList = new ArrayList<>();
        DocumentFile[] documentFiles;
        documentFiles = Objects.requireNonNull(DocumentFile.fromTreeUri(context, uri)).listFiles();
        for (DocumentFile file : documentFiles) {
            FileBean fileBean = new FileBean(file.getUri());
            fileBean.setDepth(getFileOrDirectoryDepth(file.getUri()));
            fileBean.setFileName(getFileOrDirectoryName(file.getUri()));
            fileBean.setFileSize(getFileOrDirectorySize(file) + "");
            fileBean.setFileDate(file.lastModified());
            fileList.add(fileBean);
        }
        Collections.sort(fileList, (file1, file2) -> file1.getFileName().compareTo(file2.getFileName()));
        return fileList;
    }

    /**
     * 获取文件或目录大小
     */
    public static String getFileOrDirectorySize(DocumentFile file) {
        long size;
        if (file.isDirectory()) {
            DocumentFile[] documentFiles = file.listFiles();
            size = documentFiles.length;
            return size + " 项";
        } else {
            size = file.length();
            return formatSize(size);
        }
    }

    /**
     * 格式化文件大小
     */
    private static String formatSize(double size) {
        StringBuilder result = new StringBuilder((int) size + " B");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 0; size >= 1024; i++) {
            size = size / 1024;
            switch (i) {
                case 0:
                    result = new StringBuilder(" K");
                    break;
                case 1:
                    result = new StringBuilder(" M");
                    break;
                case 2:
                    result = new StringBuilder(" G");
                    break;
                default:
            }
            result.insert(0, decimalFormat.format(size));
        }
        return result.toString();
    }
}