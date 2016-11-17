package se.hel.closepresence.service.networking.objects;

/**
 * Created by k on 2016-08-11.
 */

public class InRangeBody {
    private String api_key, beacon_uuid;
    private int id_user, major, minor, rssi;
    private int timestamp=0;

    public InRangeBody(String api_key, Integer userid, String beaconUUID, int major, int minor, int RSSI) {
        this.api_key=api_key;
        this.beacon_uuid=beaconUUID;
        this.id_user=userid;
        this.major=major;
        this.minor=minor;
        this.rssi=RSSI;
    }
}
