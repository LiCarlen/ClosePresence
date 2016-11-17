package se.hel.closepresence.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import se.hel.closepresence.R;
import se.hel.closepresence.list.UnSubscribedBeaconList;
import se.hel.closepresence.model.BeaconSubscription;
import se.hel.closepresence.service.networking.SubscribeBeaconCall;

public class SettingsActivity extends AppCompatActivity{

    private Context context = this;
    private String serialNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setttings);
        serialNo = getIntent().getStringExtra(UnSubscribedBeaconList.UUID_NO);
    }

    protected void renameBeacon(View view){
        EditText editText = (EditText) findViewById(R.id.becon_alias);
        String alias = editText.getText().toString().trim();
        String userNames = "\"" + serialNo.trim() + "\" " + ":" + "\"" + alias + "\"";
        SharedPreferences preferences = getSharedPreferences("BEACON_ALIAS", 0);
        preferences.edit().putString("BEACON_ALIAS", userNames).apply();
        Toast.makeText(this, "Beacon Registered ... to phone", Toast.LENGTH_SHORT);
    }

    protected void subscribeBeacon(View view){
        SharedPreferences userInfo = getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
        int userId = userInfo.getInt("USER_ID", -1);
        SubscribeBeaconCall subscribeBeaconCall = new SubscribeBeaconCall(this, userId,
                "84146ee9-60f3-473c-893c-322c8bbb59d8",5555, 6666);
    }

    public void subscription_success(String beaconUUID, int major, int minor) {
        //TODO: save the arguments in sharedpreferences or whatever as an entry in a collection of some sort
        SharedPreferences subscriptionprefs = getSharedPreferences("JSON_SUBSCRIBED", Context.MODE_PRIVATE);
        String subscriptionstring = subscriptionprefs.getString("JSON_SUBSCRIBED", "");
        ArrayList<BeaconSubscription> subscriptions;
        Gson gson = new Gson();
        if (!subscriptionstring.equals(""))
        {
            subscriptions = gson.fromJson(subscriptionstring, (new TypeToken<ArrayList<BeaconSubscription>>(){}.getType()));
        } else {
            subscriptions = new ArrayList<BeaconSubscription>();
        }

        subscriptions.add(new BeaconSubscription(beaconUUID, major, minor));

        subscriptionprefs.edit().putString("JSON_SUBSCRIBED", gson.toJson(subscriptions, (new TypeToken<ArrayList<BeaconSubscription>>(){}.getType()))).apply();
                //("JSON_SUBSCRIBED", "");
    }
}
