package com.leozhi.monote.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leozhi.monote.bean.FileBean;

import java.util.List;

/**
 * @author leozhi
 */
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<FileBean>> fileListLiveData;

    public MutableLiveData<List<FileBean>> getFileListLiveData() {
        if (fileListLiveData == null) {
            fileListLiveData = new MutableLiveData<>();
        }
        return fileListLiveData;
    }

    public void setFileListLiveData(List<FileBean> fileList) {
        fileListLiveData.setValue(fileList);
    }
}