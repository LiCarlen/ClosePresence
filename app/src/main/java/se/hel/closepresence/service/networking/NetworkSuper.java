package se.hel.closepresence.service.networking;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by k on 2016-08-12.
 * Superclass for all network calls.
 * Provides class variables and methods for checking if a network call has been completed, and completed successfully.
 * Additionally provides ProgressDialogs.
 * NOTE: isOk may be null if the request was not yet completed. Add checks, or at least check if isDone to avoid nullpointerexceptions
 */
public abstract class NetworkSuper<T> {
    protected boolean ok = false;
    protected boolean done = false;

    protected Context ourcontext; //Store the context so that we may dismiss the ProgressDialog when done

    protected ProgressDialog getPd() {
        return pd;
    }

    protected ProgressDialog pd;
    protected T result; //Generic result
    protected boolean isDone()
    {
        return done;
    }

    protected boolean isOk()
    {
        return ok;
    }

    protected void setOk(boolean ok) {
        this.ok = ok;
    }

    protected void setDone(boolean done) {
        this.done = done;
    }

    protected void setResult(T result)
    {
        this.result = result;
    }

    protected abstract T getResult();



}
