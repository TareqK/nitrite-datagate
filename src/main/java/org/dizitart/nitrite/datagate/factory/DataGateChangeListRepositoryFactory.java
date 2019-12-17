/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepository;
import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepositoryDummyImpl;

/**
 *
 * @author tareq
 */
public class DataGateChangeListRepositoryFactory {
    private static DataGateChangeListRepositoryFactory instance = getInstance();
    private DataGateChangeListRepositoryFactory(){
        
    }
    
    public static final DataGateChangeListRepositoryFactory getInstance(){
        if(instance == null){
            instance = new DataGateChangeListRepositoryFactory();
        }
        return instance;
    }
    
    public DataGateChangeListRepository get(String username){
        DataGateChangeListRepository repo = new DataGateChangeListRepositoryDummyImpl();
        repo.setUser(username);
        return repo;
    }
    
    
}
