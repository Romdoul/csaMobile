package com.totalmaster.csa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean userLoggedIn = preferences.getBoolean("loggedIn", false);
//        String userNmae = preferences.getString("username", null);
//        String passWord = preferences.getString("password", null);
        if(!userLoggedIn){
            navigate(LoginActivity.class);
        }else
            navigate(DashboardActivity.class);




    }

    private void navigate(final Class next) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this, next);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}

