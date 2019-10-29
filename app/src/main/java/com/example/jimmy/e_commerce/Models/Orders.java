package com.example.jimmy.e_commerce.Models;

public class Orders {
    private String Description;
    private String Name;
    private String Quantity;
    private String Price;


    public Orders() {
    }

    public Orders(String Description2, String Name2, String quantity, String price) {
        Description = Description2;
        Name = Name2;
        Quantity = quantity;
        Price = price;
    }

    public String getDescription() {
        return Description;
    }


    public void setDescription(String Description2) {
        Description = Description2;
    }

    public String getName() {
        return Name;
    }

    public void setProductName(String Name2) {
        Name = Name2;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
