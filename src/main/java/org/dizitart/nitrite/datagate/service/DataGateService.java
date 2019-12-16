/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.service;

import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
public interface DataGateService {
 
    public static final String GET_CHANGES_SINCE = "getChangesSince";
    public static final String GET_COLLECTION = "getCollection";
    public static final String CHANGE = "change";
    
    String getUserName();
    
    void setUserName(String username);
    
    ChangeList getChangesSince(String collectionName, long timeStamp);
    
    ChangeList getCollection(String collectionName);
    
    void change(ChangeList changeList, String collectionName);
    
}
