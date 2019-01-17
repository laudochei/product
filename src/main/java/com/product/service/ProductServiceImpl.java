/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.service;
/**
 *
 * @author Laud.Ochei
 */



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.product.data.PackageProductDao;
import com.product.model.PackageProduct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laud.Ochei
 */

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    
        PackageProductDao packageProductDao;
	@Autowired
	public void setPackageProductDao(PackageProductDao packageProductDao) {
		this.packageProductDao = packageProductDao;
	}

	
       @Override
        public PackageProduct findByNo(Integer productno) {    
            return packageProductDao.findByNo(productno);
        }
        
        @Override
        public PackageProduct findById(String productid) {    
            return packageProductDao.findById(productid);
        }
        
        
        @Override
	public List<PackageProduct> findAll() {
		return packageProductDao.findAll();
	}
        
                
        @Override
	public void save(PackageProduct packageProduct) {
            packageProductDao.save(packageProduct);
	}
       
        @Override
        public int idExists(String productid) {
            return packageProductDao.idExists(productid);
        }
        
               
        @Override
	public void update(PackageProduct packageProduct) {
            packageProductDao.update(packageProduct);
	}
        
        
        @Override
        public void delete(Integer productno) {    
            packageProductDao.delete(productno);
        }
        
        @Override
        public int ForeignKeyPackageProduct(String productid) {
            return  packageProductDao.ForeignKeyPackageProduct(productid);  
        }
        
        
}


