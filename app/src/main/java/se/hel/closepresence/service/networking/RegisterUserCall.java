package se.hel.closepresence.service.networking;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import se.hel.closepresence.activity.RegistrationActivity;
import se.hel.closepresence.service.networking.objects.RegisterUserBody;

/**
 * Created by k on 2016-08-12.
 */
public class RegisterUserCall extends NetworkSuper<Integer>{
    private static final String URL = "http://beacons.zenzor.io/sys/api/register_user";

    public RegisterUserCall(Context context, RegisterUserBody user) throws InterruptedException {
        Gson gson = new Gson();
        String body = gson.toJson(user);
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        ourcontext = context;
        Ion.with(context).load("POST", URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setBodyParameter("input", body) //Why are we not just sending raw json in the body? headdesk
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        pd.dismiss();
                        System.out.println("We got to onCompleted!");
                        if (e == null) {
                            result = result.substring(36, result.length());
                            result = result.substring(0, result.length()-2);
                            RegistrationActivity phonehome = (RegistrationActivity) ourcontext;
                            phonehome.registration_complete(new Integer(result));
                        } else {
                            setOk(false);
                        }
                        setDone(true);
                        //View change will have to be invoked here
                    }

                });
    }

    @Override
    public Integer getResult() {
        return result;
    }
}
