package com.unidroid.track_me_parentend.Model;

public class GPS_Data {
    private String latitude;
    private String longitude;
    private String date;
    private String time;

    public GPS_Data() {
    }

    public GPS_Data(String latitude, String longitude, String date, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
