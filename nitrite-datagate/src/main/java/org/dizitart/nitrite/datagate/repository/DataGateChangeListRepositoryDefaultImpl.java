/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.repository;

import org.apache.commons.lang3.StringUtils;
import org.dizitart.nitrite.datagate.entity.ChangeItem;
import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
public class DataGateChangeListRepositoryDefaultImpl extends DataGateChangeListRepositoryJongoImpl {

  @Override
  public void updateCollectionData(ChangeList changeList) {
    changeList.getChangeItems().parallelStream().forEach(change -> {
      if (!StringUtils.isEmpty(change.getNitriteId())) {
        if (change.getData() == null || change.getData().keySet().isEmpty()) {
          getUserCollection(changeList.getCollection()).remove("{nitriteId:{$eq:#}}", change.getNitriteId());
        } else {
          ChangeItem foundItem = getUserCollection(changeList.getCollection()).findOne("{nitriteId:{$eq:#}}", change.getNitriteId()).as(ChangeItem.class);
          if (foundItem == null || foundItem.getTimestamp() <= change.getTimestamp()) {
            getUserCollection(changeList.getCollection()).update("{nitriteId:{$eq:#}}", change.getNitriteId()).upsert().with(change);
          }
        }
      }
    });

  }

  @Override
  public ChangeList getChangesOnCollectionSince(String collection, long millis) {
    ChangeList changeList = new ChangeList();
    changeList.setCollection(collection);
    getUserCollection(collection).find("{timestamp:{$gte:#}}", millis).as(ChangeItem.class).forEach(change -> changeList.getChangeItems().add(change));
    return changeList;
  }

  @Override
  public ChangeList getCollectionData(String collection) {
    ChangeList changeList = new ChangeList();
    changeList.setCollection(collection);
    getUserCollection(collection).find().as(ChangeItem.class).forEach(change -> changeList.getChangeItems().add(change));
    return changeList;
  }

}
