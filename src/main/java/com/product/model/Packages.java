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
public class Packages {
    private Integer packageno;
    private String packageid;
    private String packagename;
    private String packagedesc;
    //private Integer productno;
    private HashMap<Integer, List<PackageProduct>> Products;
    private double packageprice;

    public Integer getPackageno() {
        return packageno;
    }

    public void setPackageno(Integer packageno) {
        this.packageno = packageno;
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getPackagedesc() {
        return packagedesc;
    }

    public void setPackagedesc(String packagedesc) {
        this.packagedesc = packagedesc;
    }

    public HashMap<Integer, List<PackageProduct>> getProducts() {
        return Products;
    }

    public void setProducts(HashMap<Integer, List<PackageProduct>> Products) {
        this.Products = Products;
    }

    public double getPackageprice() {
        return packageprice;
    }

    public void setPackageprice(double packageprice) {
        this.packageprice = packageprice;
    }

    

    

    
    
    
}
