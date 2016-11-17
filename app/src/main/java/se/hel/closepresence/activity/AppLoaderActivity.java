package se.hel.closepresence.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import se.hel.closepresence.R;

public class AppLoaderActivity extends AppCompatActivity{

    protected static final String Settings = "APP_PREFERENCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_loader);

        SharedPreferences mSharedPreferences = getSharedPreferences(Settings, Context.MODE_PRIVATE);
        Intent intent;

        boolean notFirstRun = mSharedPreferences.getBoolean(Settings, false);

        if (notFirstRun){
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, RegistrationActivity.class);
        }

        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
