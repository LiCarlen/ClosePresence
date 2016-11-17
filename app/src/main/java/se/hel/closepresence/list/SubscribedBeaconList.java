package se.hel.closepresence.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import se.hel.closepresence.R;
import se.hel.closepresence.model.BeaconSubscription;

public class SubscribedBeaconList extends AppCompatActivity {

    ListView listView;
    private Context context = this;
    ArrayList<String> subBeaconListString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_subscribed_beacon);

        listView = (ListView) findViewById(R.id.subscribed_beacons_list);

        SharedPreferences subscriptionprefs = getSharedPreferences("JSON_SUBSCRIBED", Context.MODE_PRIVATE);
        String subscriptionstring = subscriptionprefs.getString("JSON_SUBSCRIBED", "");
        ArrayList<BeaconSubscription> subscriptions;
        Gson gson = new Gson();

        if (!subscriptionstring.equals(""))
        {
            subscriptions = (ArrayList<BeaconSubscription>) gson.fromJson(subscriptionstring, (new TypeToken<ArrayList<BeaconSubscription>>(){}.getType()));
        } else {
            subscriptions = new ArrayList<BeaconSubscription>();
        }

        if (subscriptions.size() == 0) {
            return;
        }
            for (BeaconSubscription beacon: subscriptions) {
                subBeaconListString.add(beacon.getUUID());
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1, subBeaconListString);


        // assign adapter to the listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                                                // ListView Clicked item index
                                                int itemPositon = i;

                                                // ListView clicked item value
                                                String itemValue = (String) listView.getItemAtPosition(itemPositon);
                                                Toast.makeText(getApplicationContext(), "Beacon Selected : " +itemValue , Toast.LENGTH_LONG).show();

                                            }
                                        }
        );


    }
}
