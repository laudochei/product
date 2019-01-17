/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.controller;


/**
 *
 * @author Laud.Ochei
 */



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.product.exception.MessageException;
import com.product.model.PackageProduct;
import com.product.model.Packages;
import com.product.service.MessageService;
import com.product.service.PackageService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Laud.Ochei
 */

@ControllerAdvice
@RestController
@RequestMapping(value = "/packageapi")
public class PackageAPIController {
    private PackageService packageService;
	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}
        
        private MessageService messageService;
	@Autowired
	public void setMessageService(MessageService messageService) {
            this.messageService = messageService;
	}
        
        // list product
        @RequestMapping(value = "/packagelist", method=GET)
        public List<Packages> listPackage() {
            System.out.println("Check this1");
            return packageService.findAll();
        }
        
        /*
        @RequestMapping(value = "/packagedproductlist", method=GET)
        public List<PackagedProduct> listPackagedProducts() {
            System.out.println("Check this1");
            return packageService.findAllPackagedProducts();
        }
        */
        
        
        //display a single record
        @RequestMapping(value = "/{packageno}", method = RequestMethod.GET)
	public ResponseEntity<String> getPackage(@PathVariable("packageno") Integer packageno) {
            Packages packages = packageService.findByNo(packageno);
            if (packages == null) {
		return new ResponseEntity("No package found for No: " + packageno, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(packages, HttpStatus.OK);
        }
        
       
        
        //add a single record
        @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
        public ResponseEntity<Void> addPackage(@RequestBody Packages packages, UriComponentsBuilder ucb) throws MessageException {   
            int packagestatus = packageService.idExists(packages.getPackageid());     
            if (packagestatus > 0) { 
                throw new MessageException("Record already exist for Pmitem with ID: " + packages.getPackageid());
            }
            
            packageService.save(packages);
            HttpHeaders headers = new HttpHeaders();
            URI pmitemUri = ucb.path("/packageapi/").path(String.valueOf(packages.getPackageid())).build().toUri();
            headers.setLocation(pmitemUri);
            headers.add("Packageno", String.valueOf(packages.getPackageno()));
            String Msg ="Record added for Product with ID: " + packages.getPackageid();
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.CREATED);
        }


        
        //update a single record
        @RequestMapping(value = "/update/{packageno}", method = RequestMethod.PUT, headers = "Accept=application/json")
        public ResponseEntity<Void> update(@PathVariable("packageno") Integer packageno, @RequestBody Packages packages) throws MessageException {
            Packages packagestatus = packageService.findByNo(packageno);
            if (packagestatus == null) {
                throw new MessageException("No record found for package NO: " + packageno);
            } 
            if ((int)packageno != (int)packages.getPackageno()) {
                throw new MessageException("Package ID to update is in conflict with existing record : " + packages.getPackageid());
            }
            packageService.update(packages);
            String Msg ="Record updated for package ID: " + packages.getPackageid();
            HttpHeaders headers = new HttpHeaders();
            headers.add("SuccessMsg", Msg);
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.OK);
        }

        
        
        
        
                //delete a single record
        @RequestMapping(value = "/delete/{pmitemno}", method = RequestMethod.GET)
        public ResponseEntity<PackageProduct>  deletePackage(@PathVariable("packageno") Integer packageno) throws MessageException {
            Packages packagestatus = packageService.findByNo(packageno);
            if (packagestatus == null) {
                throw new MessageException("No record found for package No: " + packageno);
            }        
 
            
            
            int foreignKeyPackageProduct = packageService.ForeignKeyPackageProduct(packagestatus.getPackageid());     
            if (foreignKeyPackageProduct > 0) { 
                throw new MessageException("Record ID: " + packagestatus.getPackageid() + " cannot be deleted");
            }
            
            packageService.delete(packageno);
            throw new MessageException("Record deleted for package ID: " + packagestatus.getPackageid());
        } 

        
        
        
        @ExceptionHandler(MessageException.class)
	public ResponseEntity<com.product.model.Message> exceptionMsgHandler(Exception ex) {
            com.product.model.Message msg = new com.product.model.Message();
            msg.setMessage(ex.getMessage());
            return new ResponseEntity<com.product.model.Message>(msg, HttpStatus.OK);
	}

}
