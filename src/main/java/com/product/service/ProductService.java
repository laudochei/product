/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.service;

import com.product.model.PackageProduct;
import java.util.List;

/**
 *
 * @author Laud.Ochei
 */
public interface ProductService {
    PackageProduct findByNo(Integer productsno);
    PackageProduct findById(String productsid);
    List<PackageProduct> findAll();
    void save(PackageProduct packageProduct);
    void update(PackageProduct packageProduct);
    void delete(Integer productno);
    int idExists(String productid);
    int ForeignKeyPackageProduct(String productid);
    
}
