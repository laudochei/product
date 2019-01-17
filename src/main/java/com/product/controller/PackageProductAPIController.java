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
import com.product.service.MessageService;
import com.product.service.ProductService;
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
@RequestMapping(value = "/productapi")
public class PackageProductAPIController {
    private ProductService productService;
	@Autowired
	public void setPmitemService(ProductService productService) {
		this.productService = productService;
	}
        
        private MessageService messageService;
	@Autowired
	public void setMessageService(MessageService messageService) {
            this.messageService = messageService;
	}
        
        // list product
        @RequestMapping(value = "/productlist", method=GET)
        public List<PackageProduct> listProduct() {
            return productService.findAll();
        }
        
        //display a single record
        @RequestMapping(value = "/{productno}", method = RequestMethod.GET)
	public ResponseEntity<String> getPmitem(@PathVariable("productno") Integer productno) {
            PackageProduct product = productService.findByNo(productno);
            if (product == null) {
		return new ResponseEntity("No pmitem found for ID " + productno, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(product, HttpStatus.OK);
        }
        
       
        
        //add a single record
        @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
        public ResponseEntity<Void> addProduct(@RequestBody PackageProduct packageProduct, UriComponentsBuilder ucb) throws MessageException {   
            int productstatus = productService.idExists(packageProduct.getProductid());     
            if (productstatus > 0) { 
                throw new MessageException("Record already exist for Pmitem with ID: " + packageProduct.getProductid());
            }
            
            
            productService.save(packageProduct);
            HttpHeaders headers = new HttpHeaders();
            URI pmitemUri = ucb.path("/productapi/").path(String.valueOf(packageProduct.getProductno())).build().toUri();
            headers.setLocation(pmitemUri);
            headers.add("Productno", String.valueOf(packageProduct.getProductno()));
            String Msg ="Record added for Product with ID: " + packageProduct.getProductno();
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.CREATED);
        }


        
        //update a single record
        @RequestMapping(value = "/update/{productno}", method = RequestMethod.PUT, headers = "Accept=application/json")
        public ResponseEntity<Void> updatePmitem(@PathVariable("productno") Integer productno, @RequestBody PackageProduct packageProduct) throws MessageException {
            PackageProduct productstatus = productService.findByNo(productno);
            if (productstatus == null) {
                throw new MessageException("No record found for product ID: " + productno);
            } 
            if ((int)productno != (int)packageProduct.getProductno()) {
                throw new MessageException("Product No to update is in conflict with existing record : " + packageProduct.getProductid());
            }
            productService.update(packageProduct);
            String Msg ="Record updated for pmitem ID: " + packageProduct.getProductid();
            HttpHeaders headers = new HttpHeaders();
            headers.add("SuccessMsg", Msg);
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.OK);
        }

        
        
        
        
                //delete a single record
        @RequestMapping(value = "/delete/{pmitemno}", method = RequestMethod.GET)
        public ResponseEntity<PackageProduct>  deletePmitem(@PathVariable("productno") Integer productno) throws MessageException {
            PackageProduct productstatus = productService.findByNo(productno);
            if (productstatus == null) {
                throw new MessageException("No record found for product No: " + productno);
            }        
 
            
            
            int foreignKeyPackageProduct = productService.ForeignKeyPackageProduct(productstatus.getProductid());     
            if (foreignKeyPackageProduct > 0) { 
                throw new MessageException("Record ID: " + productstatus.getProductid() + " cannot be deleted");
            }
            
            productService.delete(productno);
            throw new MessageException("Record deleted for product ID: " + productstatus.getProductid());
        } 

        
        
        
        @ExceptionHandler(MessageException.class)
	public ResponseEntity<com.product.model.Message> exceptionMsgHandler(Exception ex) {
            com.product.model.Message msg = new com.product.model.Message();
            msg.setMessage(ex.getMessage());
            return new ResponseEntity<com.product.model.Message>(msg, HttpStatus.OK);
	}

}
