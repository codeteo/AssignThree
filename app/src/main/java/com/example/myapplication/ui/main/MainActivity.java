package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.myapplication.R;
import com.example.myapplication.ui.main.screens.BooksFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fabButton;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        fabButton = findViewById(R.id.fab);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new BooksFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_books:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new BooksFragment())
                        .commit();
                return true;
            case R.id.action_misc:
/*
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new BooksFragment())
                        .commit();
*/
                return true;
            case R.id.action_link:
                return true;
            case R.id.action_settings:
                return true;
        }
        return true;
    }
}
