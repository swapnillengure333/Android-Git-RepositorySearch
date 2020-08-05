package com.softwareladers.gitRepo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.softwareladers.gitRepo.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchFragment.newInstance())
                    .commitNow();
        }
    }
}