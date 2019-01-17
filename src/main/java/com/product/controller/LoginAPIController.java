/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.controller;


import com.product.exception.MessageException;
import com.product.model.Login;


import com.product.service.MessageService;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Laud.Ochei
 */

@ControllerAdvice
@RestController
@RequestMapping(value = "/loginapi")
public class LoginAPIController {
        
        private MessageService messageService;
	@Autowired
	public void setMessageService(MessageService messageService) {
            this.messageService = messageService;
	}
	
        //login a user
        @RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
        public ResponseEntity<Login> login(@RequestBody Login login) throws MessageException { 
            // check for empty username and password
            if(login.getUsername()==null || login.getUsername().isEmpty()){
                throw new MessageException("Username is empty");
            }
            
            if(login.getPassword()==null || login.getPassword().isEmpty()){
                throw new MessageException("Password is empty");
            }
            
            
            HttpHeaders headers = new HttpHeaders();
            String Msg = "success";
            //Integer intField = (int)user.getUserid();
            //String strField = user.getUsername(); 
            return new ResponseEntity(messageService.GetMessage(Msg), headers, HttpStatus.CREATED);
            //return new ResponseEntity(message(Msg, intField, strField), headers, HttpStatus.CREATED);
            //return new ResponseEntity(messagedetails(Msg, intField, strField), headers, HttpStatus.CREATED);
        }
        
        
        
              
        @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.DELETE})
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void logout(HttpSession session) {
            session.invalidate();       
        }
       
        @ExceptionHandler(MessageException.class)
	public ResponseEntity<com.product.model.Message> exceptionMsgHandler(Exception ex) {
            com.product.model.Message msg = new com.product.model.Message();
            msg.setMessage(ex.getMessage());
            
            return new ResponseEntity<com.product.model.Message>(msg, HttpStatus.OK);
	}
        
        
        public Map<String, Object> messagedetails (String msg, Integer intfield, String strfield) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("message", msg);
            params.put("userno", intfield);
            params.put("username", strfield);
            return params;
	}
    
}
