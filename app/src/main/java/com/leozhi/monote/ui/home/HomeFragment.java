package com.leozhi.monote.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leozhi.monote.util.FileUtils;
import com.leozhi.monote.adapter.FileListAdapter;
import com.leozhi.monote.databinding.FragmentHomeBinding;
import com.leozhi.monote.ui.MainViewModel;

/**
 * @author leozhi
 */
public class HomeFragment extends Fragment implements LifecycleObserver {
    private FragmentHomeBinding binding;
    private MainViewModel mainViewModel;
    private HomeViewModel homeViewModel;
    private FileListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new FileListAdapter();
        binding.recyclerview.setAdapter(adapter);
        mainViewModel.getRootUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null && !"".equals(uri.toString())) {
                if (homeViewModel.getFileListLiveData().getValue() == null) {
                    homeViewModel.setFileListLiveData(FileUtils.getFileOrDirList(uri, ""));
                }
            }
        });
        homeViewModel.getFileListLiveData().observe(getViewLifecycleOwner(), adapter::submitList);

        // 点击事件
        adapter.setOnItemClickListener((parent, view, position, file) -> {
            DocumentFile documentFile = DocumentFile.fromSingleUri(requireActivity(), file.getFileUri());
            assert documentFile != null;
            if (documentFile.isDirectory()) {
                homeViewModel.setFileListLiveData(FileUtils.getFileOrDirList(file.getFileUri(), file.getFilePath()));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getLifecycle().addObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().getLifecycle().removeObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        mainViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(
                requireActivity().getApplication(), this)).get(MainViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

}