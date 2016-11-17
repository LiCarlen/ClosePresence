package se.hel.closepresence.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import se.hel.closepresence.R;
import se.hel.closepresence.list.SubscribedBeaconList;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void scanBeacon(View view){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    private void showSubscribedBeacon (View view) {
        Intent intent = new Intent(this, SubscribedBeaconList.class);
        startActivity(intent);
    }

    //Now shows the user ID instead
    private void showUserInfo(View view) {
        SharedPreferences userInfo = getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
        int userId = userInfo.getInt("USER_ID", -1);
        TextView userInfo_textView = (TextView) findViewById(R.id.user_info);
        userInfo_textView.setText(String.valueOf(userId));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("back",""+1);
            super.onDestroy();
            finish();
        }
        return true;
    }
}
