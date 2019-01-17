/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.data;

import com.product.model.PackageProduct;
import java.util.List;

/**
 *
 * @author Laud.Ochei
 */
public interface PackageProductDao {
    PackageProduct findByNo(Integer productsno);
    PackageProduct findById(String productsid);
    List<PackageProduct> findAll();
    void save(PackageProduct products);
    void update(PackageProduct products);
    void delete(Integer productsno);
    int idExists(String productsid);
    int ForeignKeyPackageProduct(String productid);
}
