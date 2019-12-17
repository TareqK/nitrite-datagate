/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.repository;

import java.util.List;
import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
public interface DataGateChangeListRepository {

    String getUser();

    void setUser(String user);

    void update(ChangeList changeList);

    ChangeList getSince(String collection, long millis);

    ChangeList getAll(String collection);
}
