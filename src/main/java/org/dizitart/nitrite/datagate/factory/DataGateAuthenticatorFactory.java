/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.dizitart.nitrite.datagate.auth.DataGateAuthenticator;
import org.dizitart.nitrite.datagate.auth.DummyDataGateAuthenticatorImpl;

/**
 *
 * @author tareq
 */
public class DataGateAuthenticatorFactory {
    
    private static DataGateAuthenticatorFactory instance = getInstance();
    
    private DataGateAuthenticator authenticator = new DummyDataGateAuthenticatorImpl();
    private DataGateAuthenticatorFactory(){
        
    }
    
    public static DataGateAuthenticatorFactory getInstance(){
        if(instance == null){
            instance = new DataGateAuthenticatorFactory();
        }
        return instance;
    }
    
    public DataGateAuthenticator get(){
        return authenticator;
    }
}
