package com.leozhi.monote.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.leozhi.monote.R;
import com.leozhi.monote.databinding.ActivityMainBinding;

/**
 * @author leozhi
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavBar, controller);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}