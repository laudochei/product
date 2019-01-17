/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.service;

import com.product.model.PackagedProduct;
import com.product.model.Packages;
import java.util.List;

/**
 *
 * @author Laud.Ochei
 */
public interface PackagedProductService {
    
    PackagedProduct findByNo(Integer no);
    PackagedProduct findById(String id);
    List<PackagedProduct> findAll();
   // List<PackagedProduct> findAllPackagedProducts();
    void save(PackagedProduct packagedproduct);
    void update(PackagedProduct packagedproduct);
    void delete(Integer no);
    int idExists(String id);
    int ForeignKeyPackage(String packageid);
    int ForeignKeyProduct(String productid);
}
