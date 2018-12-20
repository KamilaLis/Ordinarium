package com.example.klis.ordinarium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.Menu;
import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navigationView;

    protected static String language_code = "en-US";
    protected static int radio_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.navigation_language:
//                startActivity(new Intent(this, MainActivity.class));
//                break;
//            case R.id.navigation_speak:
//                startActivity(new Intent(this, MainActivity.class));
//                break;
//            case R.id.navigation_read:
//                startActivity(new Intent(this, ReadActivity.class));
//                break;
//        }
//        finish();
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_language) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (itemId == R.id.navigation_speak) {
                startActivity(new Intent(this, SpeakActivity.class));
            } else if (itemId == R.id.navigation_read)
                startActivity(new Intent(this, ReadActivity.class));
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}