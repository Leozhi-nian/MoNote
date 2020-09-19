package com.leozhi.monote.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import androidx.documentfile.provider.DocumentFile;

import com.leozhi.monote.MyApplication;
import com.leozhi.monote.bean.FileBean;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author leozhi
 * @date 20-9-19
 */
public class FileUtils {
    /**
     * 获取根目录
     */
    public static String getRootDir(Uri uri) {
        DocumentFile documentFile = DocumentFile.fromTreeUri(MyApplication.getContext(), uri);
        assert documentFile != null;
        return documentFile.getUri().getLastPathSegment();
    }

    /**
     * 获取目录下的文件及目录
     */
    public static List<FileBean> getFileOrDirList(Uri uri, String path) {
        List<FileBean> fileBeanList = new ArrayList<>();
        DocumentFile documentFile = DocumentFile.fromTreeUri(MyApplication.getContext(), uri);
        DocumentFile nextFile;
        String[] pathSegments = path.split(File.separator);
        assert documentFile != null;
        for (String segment : pathSegments) {
            if (TextUtils.isEmpty(segment)) {
                continue;
            }
            nextFile = documentFile.findFile(segment);
            if (nextFile != null) {
                 documentFile = nextFile;
            }
        }
        DocumentFile[] documentFiles = documentFile.listFiles();
        for (DocumentFile file : documentFiles) {
            FileBean fileBean = new FileBean(file.getUri());
            fileBean.setFileName(getFileOrDirDisplayName(file.getUri()));
            fileBean.setFilePath(path + File.separator + getFileOrDirDisplayName(file.getUri()));
            fileBean.setFileSize(getFileOrDirSize(file));
            fileBean.setFileDate(getFileOrDirModifyDate(file));
            fileBeanList.add(fileBean);
        }
        Collections.sort(fileBeanList, (file1, file2) -> file1.getFileName().compareTo(file2.getFileName()));
        return fileBeanList;
    }

    /**
     * 获取文件或目录名
     */
    public static String getFileOrDirDisplayName(Uri uri) {
        Cursor cursor = MyApplication.getContext().getContentResolver()
                .query(uri, null, null, null, null);
        String displayName = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return displayName;
    }

    /**
     * 获取文件修改时间
     */
    public static long getFileOrDirModifyDate(DocumentFile documentFile) {
        return documentFile.lastModified();
    }

    /**
     * 获取文件或目录大小
     */
    public static String getFileOrDirSize(DocumentFile file) {
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
