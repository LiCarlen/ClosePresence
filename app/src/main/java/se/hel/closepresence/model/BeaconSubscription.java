package se.hel.closepresence.model;

/**
 * Created by k on 2016-08-18.
 */

//Model object that holds info about a beacon that we've subscribed to.
public class BeaconSubscription {

    String UUID;
    int major, minor;

    public BeaconSubscription(String beaconUUID, int major, int minor) {
        UUID = beaconUUID;
        this.major = major;
        this.minor = minor;
    }

    public String getUUID() {
        return UUID;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }


}
