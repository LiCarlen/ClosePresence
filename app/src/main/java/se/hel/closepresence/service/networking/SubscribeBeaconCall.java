package se.hel.closepresence.service.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import se.hel.closepresence.activity.SettingsActivity;
import se.hel.closepresence.service.networking.objects.SubscribeBeaconBody;

/**
 * Created by k on 2016-08-17.
 */
public class SubscribeBeaconCall extends NetworkSuper<Boolean> {
    private static final String api_key = "28742sk238sdkAdhfue243jdfhvnsa1923347"; //Should have put this in the superclass...
    private static final String URL = "http://beacons.zenzor.io/sys/api/subscribe_beacon";
    String beaconUUID;
    int major, minor;

    public SubscribeBeaconCall(Context context, Integer userid, final String beaconUUID, final int minor, final int major) {
        this.beaconUUID=beaconUUID;
        this.major=major;
        this.minor=minor;
        ourcontext = context;
        Gson gson = new Gson();
        String body = gson.toJson(new SubscribeBeaconBody(api_key, beaconUUID, userid));

        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        Ion.with(context).load("POST", URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setBodyParameter("input", body) //Why are we not just sending raw json in the body? headdesk
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                            pd.dismiss();
                            System.out.println(result);
                            result = result.substring(19, result.length());
                        System.out.println(result);
                        result = result.substring(0, 3);
                        System.out.println(result);
                            if (result.equals("200")) {
                                SettingsActivity phonehome = (SettingsActivity) ourcontext;
                                phonehome.subscription_success(beaconUUID, major, minor);
                                Toast.makeText(ourcontext, "Subscribed successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ourcontext, "Subscription failed", Toast.LENGTH_SHORT).show();
                            }

                    }

                });
    }

    @Override
    public Boolean getResult() {
        return false;
    }
}
