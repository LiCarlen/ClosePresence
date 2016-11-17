package se.hel.closepresence.service.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by k on 2016-08-12.
 */
public class TestNetworkCall {

    private static final String URL = "http://textfiles.com/100/actung.hum";
    boolean done, ok;
    int userID;
    Future<String> foo;
    Context bloo;
    public ProgressDialog pd;
    public TestNetworkCall(Context context) {
        bloo = context;
        String body = null;//Make this the gson string of a parameter RegisterUserBody
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        foo = Ion.with(context).load("GET", URL)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Toast.makeText(bloo, result, Toast.LENGTH_LONG).show();
                        System.out.println("Klart!");
                        completed(result);
                        pd.dismiss();
                    }
                });

    }

    private void completed(String result) {
            //System.out.println("Resultat : (done)" + foo.isDone());
                    System.out.println("Tomt resultat: " + result.isEmpty());
                    for(char e : result.trim().toCharArray())
                    {
                        System.out.print(e);
                    }
                    System.out.println("q");
    }
}