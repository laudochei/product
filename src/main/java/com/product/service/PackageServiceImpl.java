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



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.product.data.PackageDao;
import com.product.model.PackagedProduct;
import com.product.model.Packages;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laud.Ochei
 */

@Service("PackageService")
public class PackageServiceImpl implements PackageService {
    
        PackageDao packageDao;
	@Autowired
	public void setPackageProductDao(PackageDao packageDao) {
		this.packageDao = packageDao;
	}

	
       @Override
        public Packages findByNo(Integer packageno) {    
            return packageDao.findByNo(packageno);
        }
        
        @Override
        public Packages findById(String packageid) {    
            return packageDao.findById(packageid);
        }
        
        
        @Override
	public List<Packages> findAll() {
            
		return packageDao.findAll();
	}
        
        /*
        @Override
	public List<PackagedProduct> findAllPackagedProducts() {
		return packageDao.findAllPackagedProducts();
	}
        */
        
                
        @Override
	public void save(Packages packages) {
            packageDao.save(packages);
	}
       
        @Override
        public int idExists(String packageid) {
            return packageDao.idExists(packageid);
        }
        
               
        @Override
	public void update(Packages packages) {
            packageDao.update(packages);
	}
        
        
        @Override
        public void delete(Integer packageno) {    
            packageDao.delete(packageno);
        }
        
        
        @Override
        public int ForeignKeyPackageProduct(String packageid) {
            return  packageDao.ForeignKeyPackageProduct(packageid);  
        }
        
        
}



