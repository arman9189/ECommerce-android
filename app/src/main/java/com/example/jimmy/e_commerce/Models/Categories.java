package com.example.jimmy.e_commerce.Models;

public class Categories {
    private String ID;
    private  String Name;
    private String Image;

    public Categories() {
    }

    public Categories(String Name2, String Image,String ID) {
        Name = Name2;
        this.ID=ID;
        this.Image = Image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID2) {
        ID = ID2;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name2) {
        Name = Name2;
    }
}
