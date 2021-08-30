package com.example.test102;
public class User {
    private String ItemName;
    private String ItemSize;
    private String ItemPrice;

    public User(String Itemname,String Itemsize, String Itemprice){
        ItemName = Itemname;
        ItemSize = Itemsize;
        ItemPrice= Itemprice;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemSize() {
        return ItemSize;
    }

    public void setItemSize(String itemSize) {
        ItemSize = itemSize;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }
}

