package com.example.tienda.models;

public class Product {
    private String id;
    private String name;
    private double price;
    //private String imageUri;

    public Product(){}

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    //Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }


    //Setters
    public void setName(String name){ this.name = name; }
    public void setPrice(Double price){ this.price = price; }
}