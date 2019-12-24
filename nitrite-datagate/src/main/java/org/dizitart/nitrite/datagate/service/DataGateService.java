/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.service;

import lombok.Getter;
import lombok.Setter;
import org.dizitart.nitrite.datagate.bus.DataGateBus;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DatagateAuthenticatorFactory;
import org.dizitart.nitrite.datagate.factory.DatagateBusFactory;
import org.dizitart.nitrite.datagate.factory.DatagateChangeListRepositoryFactory;
import org.dizitart.nitrite.datagate.handler.DataGateHandler;
import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepository;

/**
 *
 * @author tareq
 */
public class DataGateService {

  public static final String GET_CHANGES_SINCE = "getChangesSince";
  public static final String GET_COLLECTION_DATA = "getCollectionData";
  public static final String CHANGE = "change";
  public static final String SUBSCRIBE = "subscribe";
  public static final String AUTHENTICATE = "authenticate";
  public static final String UNSUBSCRIBE = "unsubscribe";

  private final DataGateBus bus = DatagateBusFactory.getInstance().get();
  private DataGateChangeListRepository repository;
  @Getter
  @Setter
  private DataGateHandler session;

  /**
   * Get the username for this service instance
   *
   * @return the username for this service instance
   */
  public String getUserName() {
    return this.session.getUser();
  }

  private DataGateChangeListRepository getRepository() {
    if (repository == null) {
      repository = DatagateChangeListRepositoryFactory.getInstance().get(getUserName());
    }
    return repository;
  }

  /**
   * Gets the list of changes since a timestamp
   *
   * @param collection the name of the collection to get the chagnes for
   * @param timestamp the timestamp in milliseconds since epoch to start looking from
   * @return a changelist
   */
  public ChangeList getChangesSince(String collection, long timestamp) {
    return getRepository().getChangesOnCollectionSince(collection, timestamp);
  }

  /**
   * Get all the entries in a collection
   *
   * @param collection the name of the collection
   * @return a chagne list
   */
  public ChangeList getCollection(String collection) {
    return getRepository().getCollectionData(collection);
  }

  /**
   * Subscribes the session to changes in a collection
   *
   * @param collection the collection to subscribeToBus to
   * @param listenerId the ID of the listener on the client side
   */
  public void subscribe(String collection, String listenerId) {
    session.subscribeToCollection(collection, listenerId);
  }

  /**
   * Unsubscribes the session to changes in a collection
   *
   * @param collection the collection to unsubscribeFromBus from
   */
  public void unsubscribe(String collection) {
    session.unsubscribeFromCollection(collection);
  }

  /**
   * A method that changes the database and notifies all the user's sessions
   *
   * @param changeList the list of changes
   */
  public void change(ChangeList changeList) {
    getRepository().updateCollectionData(changeList);
    bus.publish(changeList, getUserName());

  }

  /**
   * Attempts to authenticate the session in this service. Additionally, if authenticated, sets the username property of the session
   *
   * @param username the username to authenticate
   * @param password the password to try
   * @return true if authenticated, false otherwise
   */
  public boolean authenticate(String username, String password) {
    boolean authenticated = DatagateAuthenticatorFactory.getInstance().get().authenticate(username, password);
    this.session.setAuthenticated(authenticated);
    if (authenticated) {
      this.session.setUser(username);
      this.session.subscribeToBus();
    }
    return authenticated;
  }

}
