package se.hel.closepresence.model;

import java.io.Serializable;

/**
 * Created by mdshafi on 16/08/16.
 */
public class Beacon implements Serializable{

    private String name;
    private String proximityUUID;

    private String serialNumber;
    private String rssi;

    public Beacon(String name, String proximityUUID, String serialNumber, String rssi) {
        this.name = name;
        this.proximityUUID = proximityUUID;
        this.serialNumber = serialNumber;
        this.rssi = rssi;
    }


    public String getName() {
        return name;
    }

    public String getProximityUUID() {
        return proximityUUID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getRssi() {
        return rssi;
    }



}
