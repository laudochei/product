/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.service;

import com.product.model.Packages;
import java.util.List;

/**
 *
 * @author Laud.Ochei
 */
public interface PackageService {
    
    Packages findByNo(Integer packageno);
    Packages findById(String packageid);
    List<Packages> findAll();
   // List<PackagedProduct> findAllPackagedProducts();
    void save(Packages packages);
    void update(Packages packageno);
    void delete(Integer packageno);
    int idExists(String packageid);
    int ForeignKeyPackageProduct(String packageid);
}
