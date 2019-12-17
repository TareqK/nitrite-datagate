/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.service;

import org.dizitart.nitrite.datagate.bus.DataGateBus;
import org.dizitart.nitrite.datagate.session.DataGateSession;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DataGateBusFactory;
import org.dizitart.nitrite.datagate.factory.DataGateChangeListRepositoryFactory;
import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepository;

/**
 *
 * @author tareq
 */
public class DataGateServiceDummyImpl implements DataGateService {

    private String username;
    private DataGateBus bus = DataGateBusFactory.getInstance().get();
    private  DataGateChangeListRepository getRepository(){
         return DataGateChangeListRepositoryFactory.getInstance().get(getUserName());
    }
    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public ChangeList getChangesSince(String collectionName, long timeStamp) {
        return getRepository().getSince(collectionName, timeStamp);
    }

    @Override
    public ChangeList getCollection(String collectionName) {
        return getRepository().getAll(collectionName);
    }

    @Override
    public void change(ChangeList changeList) {
        getRepository().update(changeList);
        bus.publish(changeList, getUserName());
    }

    @Override
    public void subscribe(DataGateSession endpoint , String collectionName) {
        endpoint.listenToCollection(collectionName);
    }

}
