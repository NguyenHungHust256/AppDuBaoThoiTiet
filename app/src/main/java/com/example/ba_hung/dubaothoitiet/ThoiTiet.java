package com.example.ba_hung.dubaothoitiet;

/**
 * Created by ba-hung on 11/12/2017.
 */

public class ThoiTiet {
    private String day;
    private String status;
    private String image;
    private String maxTemp;
    private String minTemp;

    public ThoiTiet(String day, String status, String image, String maxTemp, String minTemp) {
        this.day = day;
        this.status = status;
        this.image = image;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDay() {
        return day;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }
}
