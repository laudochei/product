/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.data;

import com.product.model.Message;

/**
 *
 * @author Laud.Ochei
 */
public interface MessageDao {
    Message GetMessage(String msg);
    
}
