package se.hel.closepresence.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import se.hel.closepresence.R;
import se.hel.closepresence.service.networking.RegisterUserCall;
import se.hel.closepresence.service.networking.objects.RegisterUserBody;

public final class RegistrationActivity extends AppCompatActivity{

    private EditText firstName;
    private EditText lastName;
    private Button register;

    SharedPreferences settings, userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    protected void register(View view){
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        try {
            new RegisterUserCall(this, new RegisterUserBody(firstName.getText().toString(), lastName.getText().toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void registration_complete(Integer userID) {
        userInfo = getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
        userInfo.edit().putInt("USER_ID", userID.intValue()).apply();

        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();

        settings = getSharedPreferences(AppLoaderActivity.Settings, Context.MODE_PRIVATE );
        settings.edit().putBoolean(AppLoaderActivity.Settings, true).apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
