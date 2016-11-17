package se.hel.closepresence.service.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import se.hel.closepresence.service.networking.objects.OutOfRangeBody;

/**
 * Created by k on 2016-08-18.
 */
public class OutOfRangeCall extends NetworkSuper{
    private static final String api_key = "28742sk238sdkAdhfue243jdfhvnsa1923347"; //Should have put this in the superclass...
    private static final String URL = "http://beacons.zenzor.io/sys/api/beacon_nearby";

    protected OutOfRangeCall(Context context, Integer userid, final String beaconUUID) {
        ourcontext = context;
        Gson gson = new Gson();
        String body = gson.toJson(new OutOfRangeBody(api_key, userid, beaconUUID));

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
                        result = result.substring(19, result.length());
                        result = result.substring(0, 2);
                        if (result == "200") {
                            Toast.makeText(ourcontext, "Successfully reported as out of range of beacon.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ourcontext, "Failed to report as out of range.", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    @Override
    public Object getResult() {
        return null;
    }
}
