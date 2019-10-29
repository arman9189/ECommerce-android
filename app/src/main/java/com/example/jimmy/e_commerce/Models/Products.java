package com.example.jimmy.e_commerce.Models;

public class Products {
    private String Description;
    private String Name;
    private String Price;
    private String Image;
    private String Quantity;
    private String MenuId;
    private String ID;

    public Products() {
    }

    public Products(String description, String name, String price, String image, String quantity, String menuId, String id) {
        Description = description;
        Name = name;
        Price = price;
        Image = image;
        Quantity = quantity;
        MenuId = menuId;
        ID=id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID2) {
        this.ID = ID2;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String pro_id) {
        this.Description = pro_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String MenuId) {
        this.MenuId = MenuId;}
}
