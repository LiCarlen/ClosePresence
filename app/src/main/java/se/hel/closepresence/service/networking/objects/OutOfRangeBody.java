package se.hel.closepresence.service.networking.objects;

/**
 * Created by k on 2016-08-18.
 */
public class OutOfRangeBody {


    String api_key, beacon_uuid;
    int id_user;
    int timestamp = 0;

    public OutOfRangeBody(String api_key, Integer userid, String beaconUUID) {
        this.api_key=api_key;
        this.beacon_uuid=beaconUUID;
        this.id_user=userid;
    }




}
