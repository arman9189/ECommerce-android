package com.example.jimmy.e_commerce.Models;

public class Chart {
    private String Date;
    private String ID;
    private String Location;
    private String Time;
    private String TotalPrice;

    public Chart() {
    }

    public Chart(String date, String ID, String location, String time, String totalPrice) {
        Date = date;
        this.ID = ID;
        Location = location;
        Time = time;
        TotalPrice = totalPrice;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
