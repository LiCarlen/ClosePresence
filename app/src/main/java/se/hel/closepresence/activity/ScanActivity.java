package se.hel.closepresence.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.hel.closepresence.R;
import se.hel.closepresence.list.UnSubscribedBeaconList;
import se.hel.closepresence.model.Beacon;

@TargetApi(23) 
public class ScanActivity extends AppCompatActivity {

    public static final String SERVICE_UUID = "19721006-2004-2007-2014-acc0cbeac000";
    private String serialNo;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_MSG = "se.hel.closepresence.EXTRA_MSG";

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 3000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    private ArrayList<Beacon> scanResults = new ArrayList<>();

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unsubscribed_beacon);
        mHandler = new Handler();

        // check to determine whether BLE is supported on the device.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Android M Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This App Needs Location Access");
                builder.setMessage("Please Grant Locaion Access");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

    }

    // required for Android M. A callback to the app telling you the result of what the user decided
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not " +
                            "be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                        .build();
                ScanFilter closeBeaconFilter = new ScanFilter.Builder()
                        .setDeviceName("closebeacon.com").build();
                filters = new ArrayList<>();
                filters.add(closeBeaconFilter);
            }
            scanLeDevice(true);
        }
        for (Beacon beacon : scanResults) {
            System.out.println("scanResults.....:" + beacon.getSerialNumber());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            scanLeDevice(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (mGatt == null) {
            return;
        }
        mGatt.close();
        mGatt = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                //Bluetooth not enabled.
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                // Stops scanning after a pre-defined scan period.
                @Override
                public void run() {
                        mLEScanner.stopScan(mScanCallback);
                }
            }, SCAN_PERIOD);
            mLEScanner.startScan(filters, settings, mScanCallback);
        } else {
                mLEScanner.stopScan(mScanCallback);
        }
    }

////////////////////////////////    call back      ///////////////////////////////////////////////////////////

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d("callbackType", String.valueOf(callbackType));
            Log.d("result", result.toString());
            serialNo = hexToIntSerialNo(result.getDevice().toString());

            final List<ParcelUuid> serviceUuids = result.getScanRecord().getServiceUuids();

            if (serviceUuids == null && newDeviceFound(serialNo)) {
                Beacon beacon = new Beacon(result.getDevice().getName(),
                        "UUID",
                        serialNo, String.valueOf(result.getRssi()));
                scanResults.add(beacon);
            }
            displayScanResult();
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.d("ScanResult - ", sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.d("Scan Failed ", "Error Code: " + errorCode);
        }
    };


    protected String hexToIntSerialNo(String deviceHexSerialNo){
        StringBuilder bleDeviceIntString = new StringBuilder();
        String[] hexArr = deviceHexSerialNo.split(":");
        for (int i=0; i<hexArr.length; i++){
            Integer temp = Integer.parseInt(hexArr[i].trim(), 16);
            if (temp < 100){
                bleDeviceIntString.append("0");
            }
            bleDeviceIntString.append(temp.toString());
            if (i==hexArr.length-1){
                continue;
            }
            else{
                bleDeviceIntString.append("-");
            }
        }
        return bleDeviceIntString.toString();
    }

    protected void displayScanResult(){

        Intent intent = new Intent(this, UnSubscribedBeaconList.class);

        intent.putExtra(EXTRA_MSG, scanResults);
        startActivity(intent);
    }

    protected boolean newDeviceFound(String serialNo) {
        if (scanResults.size() != 0) {
            final Iterator<Beacon> it = scanResults.iterator();

            while (it.hasNext()){
                final Beacon beacon = it.next();
                final String beaconSerialNo = beacon.getSerialNumber().toString();
                final boolean bool = serialNo.equals(beaconSerialNo);

                if (bool) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("back",""+1);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
        return true;
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
}
