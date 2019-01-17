/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.controller;

import com.product.exception.MessageException;
import com.product.model.PackageProduct;
import com.product.model.PackagedProduct;
import com.product.service.MessageService;
import com.product.service.PackagedProductService;
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
@RequestMapping(value = "/packagedproductapi")
public class PackagedProductController {
    private PackagedProductService packagedproductService;
	@Autowired
	public void setPackagedProductService(PackagedProductService packagedproductService) {
		this.packagedproductService = packagedproductService;
	}
        
        private MessageService messageService;
	@Autowired
	public void setMessageService(MessageService messageService) {
            this.messageService = messageService;
	}
        
        // list product
        @RequestMapping(value = "/packagelist", method=GET)
        public List<PackagedProduct> listPackage() {
            return packagedproductService.findAll();
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
	public ResponseEntity<String> getPackage(@PathVariable("no") Integer no) {
            PackagedProduct packagedproduct = packagedproductService.findByNo(no);
            if (packagedproduct == null) {
		return new ResponseEntity("No packages and products found for No: " + no, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(packagedproduct, HttpStatus.OK);
        }
        
       
        
        //add a single record
        @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
        public ResponseEntity<Void> addPackage(@RequestBody PackagedProduct packagedproduct, UriComponentsBuilder ucb) throws MessageException {   
            int packagestatus = packagedproductService.idExists(packagedproduct.getPackagesid());     
            if (packagestatus > 0) { 
                throw new MessageException("Record already exist for package ID: " + packagedproduct.getPackagesid());
            }
            
            
            
            int productstatus = packagedproductService.idExists(packagedproduct.getProductid());     
            if (productstatus > 0) { 
                throw new MessageException("Record already exist for product ID: " + packagedproduct.getProductid());
            }
            
            packagedproductService.save(packagedproduct);
            HttpHeaders headers = new HttpHeaders();
            URI pmitemUri = ucb.path("/packageapi/").path(String.valueOf(packagedproduct.getPackageproductno())).build().toUri();
            headers.setLocation(pmitemUri);
            headers.add("No:", String.valueOf(packagedproduct.getPackageproductno()));
            String Msg ="Record added for Package with ProductID: " + packagedproduct.getProductid();
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.CREATED);
        }


        
        //update a single record
        @RequestMapping(value = "/update/{packageno}", method = RequestMethod.PUT, headers = "Accept=application/json")
        public ResponseEntity<Void> update(@PathVariable("packageno") Integer no, @RequestBody PackagedProduct packagedproduct) throws MessageException {
            PackagedProduct packagestatus = packagedproductService.findByNo(no);
            if (packagestatus == null) {
                throw new MessageException("No record found for package NO: " + no);
            } 
            if ((int)no != (int)packagedproduct.getPackageproductno()) {
                throw new MessageException("Package ID to update is in conflict with existing record : " + packagedproduct.getPackageproductno());
            }
            packagedproductService.update(packagedproduct);
            String Msg ="Record updated for ID: " + packagedproduct.getPackageproductid();
            HttpHeaders headers = new HttpHeaders();
            headers.add("SuccessMsg", Msg);
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.OK);
        }

        
        
        
        
                //delete a single record
        @RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
        public ResponseEntity<PackageProduct>  deletePackage(@PathVariable("no") Integer no) throws MessageException {
            PackagedProduct packagestatus = packagedproductService.findByNo(no);
            if (packagestatus == null) {
                throw new MessageException("No record found for package No: " + no);
            }        
 
            
            
            int foreignKeyPackage = packagedproductService.ForeignKeyPackage(packagestatus.getPackageproductid());     
            if (foreignKeyPackage > 0) { 
                throw new MessageException("Record ID: " + packagestatus.getPackageproductid() + " cannot be deleted");
            }
            
            
            int foreignKeyProduct = packagedproductService.ForeignKeyProduct(packagestatus.getProductid());     
            if (foreignKeyProduct > 0) { 
                throw new MessageException("Record ID: " + packagestatus.getProductid() + " cannot be deleted");
            }
            
            packagedproductService.delete(no);
            throw new MessageException("Record deleted for package ID: " + packagestatus.getPackageproductno());
        } 

        
        
        
        @ExceptionHandler(MessageException.class)
	public ResponseEntity<com.product.model.Message> exceptionMsgHandler(Exception ex) {
            com.product.model.Message msg = new com.product.model.Message();
            msg.setMessage(ex.getMessage());
            return new ResponseEntity<com.product.model.Message>(msg, HttpStatus.OK);
	}

}
