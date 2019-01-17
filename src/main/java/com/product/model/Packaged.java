/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.model;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Laud.Ochei
 */
public class Packaged {
    private Integer No;
    private String ID;
    private String Name;
    private String Description;
    private HashMap<Integer, List<PackageProduct>> Products;
    private double Price;

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer No) {
        this.No = No;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public HashMap<Integer, List<PackageProduct>> getProducts() {
        return Products;
    }

    public void setProducts(HashMap<Integer, List<PackageProduct>> Products) {
        this.Products = Products;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
    
    
    
    
    
    
}
