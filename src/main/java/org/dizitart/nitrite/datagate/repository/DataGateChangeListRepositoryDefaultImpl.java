/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.repository;

import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
public class DataGateChangeListRepositoryDefaultImpl extends DataGateChangeListRepository {

  @Override
  public void updateCollectionData(ChangeList changeList) {
  }

  @Override
  public ChangeList getChangesOnCollectionSince(String collection, long millis) {
    return new ChangeList();
  }

  @Override
  public ChangeList getCollectionData(String collection) {
    return new ChangeList();
  }

}
