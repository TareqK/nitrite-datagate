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
public class DataGateServiceDummyImpl implements DataGateService {

    private String username;

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
        return new ChangeList();
    }

    @Override
    public ChangeList getCollection(String collectionName) {
        return new ChangeList();
    }

    @Override
    public void change(ChangeList changeList, String collectionName) {
    }

}
