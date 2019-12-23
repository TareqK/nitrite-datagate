/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.repository;

import org.dizitart.nitrite.datagate.factory.JongoConnectionFactory;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 *
 * @author tareq
 */
public abstract class DataGateChangeListRepositoryJongoImpl extends DataGateChangeListRepository {

  private Jongo jongo;
  private static final String COLLECTION_FORMAT = "%s@%s";

  public Jongo getConnection() {
    if (jongo == null) {
      jongo = JongoConnectionFactory.getInstance().get();
    }
    return jongo;
  }

  public MongoCollection getUserCollection(String collectionName) {
    MongoCollection collection = getConnection().getCollection(String.format(COLLECTION_FORMAT, collectionName, this.getUser()));
    collection.ensureIndex("nitriteId");
    return collection;
  }

}
