/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.repository;

import java.util.ArrayList;
import java.util.List;
import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
public class DataGateChangeListRepositoryDummyImpl implements DataGateChangeListRepository {

    private String user;
    @Override
    public void update(ChangeList changeList) {
    }

    @Override
    public ChangeList getSince(String collection, long millis) {
        return new ChangeList();
    }

    @Override
    public ChangeList getAll(String collection) {
        return new ChangeList();
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }
    
}
