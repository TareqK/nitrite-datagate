/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.dizitart.nitrite.datagate.bus.DataGateBus;
import org.dizitart.nitrite.datagate.bus.DataGateBusDummyImpl;

/**
 *
 * @author tareq
 */
public class DataGateBusFactory {
    
    private static DataGateBusFactory instance = getInstance();
    private DataGateBus bus = new DataGateBusDummyImpl();
    private DataGateBusFactory(){
        
    }
    public static final DataGateBusFactory getInstance(){
        if(instance == null){
            instance = new DataGateBusFactory();
        }
        return instance;
    }
    
    public DataGateBus get(){
        return bus;
    }
    
    
    
}
