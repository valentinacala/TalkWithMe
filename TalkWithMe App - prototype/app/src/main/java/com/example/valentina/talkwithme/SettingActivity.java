package com.example.valentina.talkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SettingActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        //to have tht when the back button is pressed, the app in not closed but it returns to the parent activity
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id ==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);

    }

}
