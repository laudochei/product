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
public class PackagedProduct {
    private Integer packageproductno;
    private String packageproductid;
    private String productid;
    private String packagesid;
    private HashMap<Integer, List<PackageProduct>> ProductList;
    private HashMap<Integer, PackageProduct> Product;
    private double TotalPrice;
    

    public Integer getPackageproductno() {
        return packageproductno;
    }

    public void setPackageproductno(Integer packageproductno) {
        this.packageproductno = packageproductno;
    }

    public String getPackageproductid() {
        return packageproductid;
    }

    public void setPackageproductid(String packageproductid) {
        this.packageproductid = packageproductid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPackagesid() {
        return packagesid;
    }

    public void setPackagesid(String packagesid) {
        this.packagesid = packagesid;
    }

    
    
    public HashMap<Integer, List<PackageProduct>> getProductList() {
        return ProductList;
    }

    public void setProductList(HashMap<Integer, List<PackageProduct>> ProductList) {
        this.ProductList = ProductList;
    }

    public HashMap<Integer, PackageProduct> getProduct() {
        return Product;
    }

    public void setProduct(HashMap<Integer, PackageProduct> Product) {
        this.Product = Product;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    

    

    
     

    
    
    
    
}
