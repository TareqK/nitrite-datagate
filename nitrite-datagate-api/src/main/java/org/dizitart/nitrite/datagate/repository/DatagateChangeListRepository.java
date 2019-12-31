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
public abstract class DatagateChangeListRepository {

  private String user;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  /**
   * Updates the database with these changes
   *
   * @param changeList the changelist to apply to the database
   */
  public abstract void updateCollectionData(ChangeList changeList);

  /**
   * Gets all changes since a timestamp
   *
   * @param collection the collection to get changes on
   * @param millis the time stamp in millisceonds since epoch to look after
   * @return a change list
   */
  public abstract ChangeList getChangesOnCollectionSince(String collection, long millis);

  /**
   * Gets all the entries in a collection
   *
   * @param collection the collection to get the entries for
   * @return a change list containing all the collection's data
   */
  public abstract ChangeList getCollectionData(String collection);
}
