/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.service;

import com.product.data.PackagedProductDao;
import com.product.model.PackagedProduct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laud.Ochei
 */

@Service("PackagedProductService")
public class PackagedProductServiceImpl implements PackagedProductService {
    
        PackagedProductDao packagedProductDao;
	@Autowired
	public void setPackageProductDao(PackagedProductDao packagedProductDao) {
		this.packagedProductDao = packagedProductDao;
	}

	
       @Override
        public PackagedProduct findByNo(Integer no) {    
            return packagedProductDao.findByNo(no);
        }
        
        @Override
        public PackagedProduct findById(String id) {    
            return packagedProductDao.findById(id);
        }
        
        
        @Override
	public List<PackagedProduct> findAll() {
		return packagedProductDao.findAll();
	}
        
                
        @Override
	public void save(PackagedProduct packagedProduct) {
            packagedProductDao.save(packagedProduct);
	}
       
        @Override
        public int idExists(String productid) {
            return packagedProductDao.idExists(productid);
        }
        
               
        @Override
	public void update(PackagedProduct packagedProduct) {
            packagedProductDao.update(packagedProduct);
	}
        
        
        @Override
        public void delete(Integer no) {    
            packagedProductDao.delete(no);
        }
        
        @Override
        public int ForeignKeyPackage(String id) {
            return  packagedProductDao.ForeignKeyPackage(id);  
        }
        
        
        @Override
        public int ForeignKeyProduct(String id) {
            return  packagedProductDao.ForeignKeyProduct(id);  
        }
        
}



