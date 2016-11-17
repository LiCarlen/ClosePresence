package se.hel.closepresence.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.altbeacon.beacon.Beacon;

import se.hel.closepresence.R;

public class BeaconInfoActivity extends AppCompatActivity {

    private Beacon mBeacon;         //altBeacon.beacon
    private String proximityUuid;
    private String major;
    private String minor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_info);

        proximityUuid = mBeacon.getId1().toString();
        major = mBeacon.getId2().toString();
        minor = mBeacon.getId3().toString();

        System.out.println("ProximityUUID"+ proximityUuid);
        System.out.println("Major"+ major);
        System.out.println("Minor"+ major);

        EditText uuidText = (EditText) findViewById(R.id.proximity_uuid);
        uuidText.setText(proximityUuid);

        EditText majorText = (EditText) findViewById(R.id.major);
        majorText.setText(major);

        EditText minorText = (EditText) findViewById(R.id.minor);
        minorText.setText(minor);
    }




}
