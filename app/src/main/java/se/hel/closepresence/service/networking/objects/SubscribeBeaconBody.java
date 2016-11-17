package se.hel.closepresence.service.networking.objects;

/**
 * Created by k on 2016-08-11.
 */


public class SubscribeBeaconBody {
    private String api_key, beacon_uuid;
    private int id_user;

    public SubscribeBeaconBody(String api_key, String beacon_uuid, int id_user) {
        this.api_key = api_key;
        this.beacon_uuid = beacon_uuid;
        this.id_user = id_user;
    }
}
