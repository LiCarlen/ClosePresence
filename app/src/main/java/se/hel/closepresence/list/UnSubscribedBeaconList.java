package se.hel.closepresence.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import se.hel.closepresence.R;
import se.hel.closepresence.activity.MainActivity;
import se.hel.closepresence.activity.ScanActivity;
import se.hel.closepresence.activity.SettingsActivity;
import se.hel.closepresence.model.Beacon;


public class UnSubscribedBeaconList extends AppCompatActivity {

    public static final String UUID_NO = "se.hel.closepresence.UUID_NUM";
    private Context context = this;
    private ListView listView;
    private ArrayList<Beacon> scanResult = new ArrayList<>();
    private ArrayList<String> scanResultHex = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unsubscribed_beacon);

        listView = (ListView) findViewById(R.id.unsubscribed_beacons_list);

        scanResult = (ArrayList<Beacon>) getIntent().getSerializableExtra(ScanActivity.EXTRA_MSG);

        ArrayList<String> scanResultInt = new ArrayList<>();

        if (scanResult.size() != 0 ) {
            for (Beacon beacon: scanResult) {
            scanResultHex.add(beacon.getSerialNumber()+ "        " + beacon.getRssi() + "        " + " + ");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1, scanResultHex);

        // assign adapter to the listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                                                // ListView Clicked item index
                                                int itemPositon = i;

                                                // ListView clicked item value
                                                String itemValue = (String) listView.getItemAtPosition(itemPositon);
                                                beaconSettings(itemValue);
//                                                Toast.makeText(getApplicationContext(), "Beacon Selected : " +itemValue , Toast.LENGTH_LONG).show();

                                            }
                                        }
        );
    }

    private void beaconSettings(String listItemValue){
        String serialNo = null;
        serialNo = listItemValue.substring(0, listItemValue.indexOf(" "));
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(UUID_NO, serialNo);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    protected void refresh(MenuItem item) {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("back",""+1);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
//            finish();
        }
        return true;
    }
}
