/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.data;

import com.product.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Laud.Ochei
 */




@Repository
public class MessageDaoImpl implements MessageDao {
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
        
        
        
        
        @Override
	public Message GetMessage(String successMsg) {
            Message msg = new Message();
            msg.setMessage(successMsg);
            //String Msg = "";
            return msg;
	}
        
        
        
    
        
      

}