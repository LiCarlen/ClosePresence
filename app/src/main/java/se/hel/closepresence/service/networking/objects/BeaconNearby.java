package se.hel.closepresence.service.networking.objects;

/**
 * Created by k on 2016-08-11.
 */

import java.security.Timestamp;

public class BeaconNearby {
    private String api_key, beacon_uuid;
    private int id_user, major, minor, rssi;
    private final Timestamp timestamp;

    private BeaconNearby() {
        timestamp = null;
    }
}
