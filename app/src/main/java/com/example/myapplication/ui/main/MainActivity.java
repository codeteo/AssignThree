package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.myapplication.R;
import com.example.myapplication.ui.main.screens.BooksFragment;
import com.example.myapplication.ui.main.screens.settings.SettingsFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabButton;
    private FrameLayout frameLayout;

    private Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabButton = findViewById(R.id.fab);

        fragment = new BooksFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, fragment)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_books:
                    fragment = new BooksFragment();
                    break;
                case R.id.action_misc:
                    break;
                case R.id.action_link:
                    break;
                case R.id.action_settings:
                    fragment = new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();

            return true;
        });
    }
}
