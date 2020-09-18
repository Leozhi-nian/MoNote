package com.leozhi.monote.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;

import com.leozhi.monote.databinding.FragmentMineBinding;

/**
 * @author leozhi
 */
public class MineFragment extends Fragment implements LifecycleObserver {
    private FragmentMineBinding binding;

    private MineViewModel mViewModel;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        mViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        // TODO: Use the ViewModel
        Log.e("tag","onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.e("tag","onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.e("tag","onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        Log.e("tag","onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.e("tag","onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.e("tag","onDestroy");
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}