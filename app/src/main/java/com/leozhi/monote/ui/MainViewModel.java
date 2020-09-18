package com.leozhi.monote.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

/**
 * @author leozhi
 * @date 20-9-17
 */
public class MainViewModel extends AndroidViewModel {
    private final static String KEY_PREFERENCES_NAME = "MY_PREFERENCES";
    private final static String KEY_HAS_PREFERENCES = "HAS_PREFERENCES";
    private final static String KEY_ROOT_URI = "ROOT_URI";
    private final static String KEY_STORAGE_PERMISSION_STATE = "STORAGE_PERMISSION_STATE";
    private SavedStateHandle handle;
    private Application application;
    private SharedPreferences preferences;

    public MainViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.application = application;
        this.handle = handle;
        preferences = getApplication().getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean hasPreferences = preferences.getBoolean(KEY_HAS_PREFERENCES, false);
        if (!hasPreferences) {
            preferences.edit().putBoolean(KEY_HAS_PREFERENCES, true).apply();
            saveData();
        }
        loadData();
    }

    /**
     * 获取根目录Uri
     */
    public MutableLiveData<Uri> getRootUri() {
        return handle.getLiveData(KEY_ROOT_URI);
    }

    /**
     * 设置根目录Uri
     */
    public void setRootUri(Uri uri) {
        handle.set(KEY_ROOT_URI, uri);
    }

    /**
     * 获取存储权限状态
     * @return boolean
     */
    public MutableLiveData<Boolean> getStoragePermissionState() {
        return handle.getLiveData(KEY_STORAGE_PERMISSION_STATE);
    }

    /**
     * 设置存储权限状态
     */
    public void setStoragePermissionState(boolean state) {
        handle.set(KEY_STORAGE_PERMISSION_STATE, state);
    }

    public void loadData() {
        handle.set(KEY_ROOT_URI, Uri.parse(preferences.getString(KEY_ROOT_URI, "")));
        handle.set(KEY_STORAGE_PERMISSION_STATE, preferences.getBoolean(KEY_STORAGE_PERMISSION_STATE, false));
    }

    public void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        if (getRootUri().getValue() != null) {
            editor.putString(KEY_ROOT_URI, getRootUri().getValue().toString());
        } else {
            editor.putString(KEY_ROOT_URI, "");
        }
        if (getStoragePermissionState().getValue() != null) {
            editor.putBoolean(KEY_STORAGE_PERMISSION_STATE, getStoragePermissionState().getValue());
        } else {
            editor.putBoolean(KEY_STORAGE_PERMISSION_STATE, false);
        }
        editor.apply();
    }
}
