package com.leozhi.monote.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.leozhi.monote.R;
import com.leozhi.monote.databinding.ActivityMainBinding;
import com.leozhi.monote.util.FileUtils;

import java.util.Objects;

/**
 * @author leozhi
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private FileUtils fileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(getApplication(), this)).get(MainViewModel.class);

        fileUtils = FileUtils.getInstance(mainViewModel.getRootUri().getValue());

        // 判断权限
        if (Objects.requireNonNull(mainViewModel.getStoragePermissionState().getValue())) {
            try {
                int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                ContentResolver resolver = getContentResolver();
                if (mainViewModel.getRootUri().getValue() != null) {
                    resolver.takePersistableUriPermission(mainViewModel.getRootUri().getValue(), takeFlags);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        // 底部导航栏切换
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavBar, controller);

        binding.fab.setOnClickListener(v -> {
            if (!Objects.requireNonNull(mainViewModel.getStoragePermissionState().getValue())) {
                requestStoragePermission();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainViewModel.saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void requestStoragePermission() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        mainViewModel.setStoragePermissionState(true);
                        mainViewModel.setRootUri(data.getData());
                        // content://com.android.externalstorage.documents/tree/primary%3A
                        // primary:
                        // content://com.android.externalstorage.documents/tree/home%3A
                        // home:
                        // content://com.android.externalstorage.documents/tree/primary%3ADownload
                        // primary:Download
                        // content://com.android.externalstorage.documents/tree/primary%3ALeozhi
                        // primary:Leozhi
                        // content://com.android.providers.downloads.documents/tree/downloads
                        // downloads
                        // content://com.android.providers.downloads.documents/tree/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FMiDrive
                        // raw:/storage/emulated/0/Download/MiDrive
                    }
                }
            });
}