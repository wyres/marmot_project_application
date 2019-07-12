package com.example.trapconnectwyres;

public class Cages {
    public  String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(String battery_level) {
        this.battery_level = battery_level;
    }

    public String getLastRx() {
        return lastRx;
    }

    public void setLastRx(String lastRx) {
        this.lastRx = lastRx;
    }

    public String getLast_door_change() {
        return last_door_change;
    }

    public void setLast_door_change(String last_door_change) {
        this.last_door_change = last_door_change;
    }

    public  String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }

    public  String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String hashedId;
    String state;
    String rssi;
    String battery_level;
    String lastRx;
    String last_door_change;
    String lontitude;
    String latitude;
}
