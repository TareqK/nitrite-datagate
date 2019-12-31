/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.repository;

import com.mongodb.DuplicateKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.entity.user.DatagateUser;
import org.dizitart.nitrite.datagate.impl.factory.JongoConnectionFactory;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 *
 * @author tareq
 */
public class DatagateUserRepositoryJongoImpl implements DatagateUserRepository {

  private Jongo jongo;
  private static final String USER_COLLECTION = "datagate-users";
  private static final Logger LOG = Logger.getLogger(DatagateUserRepositoryJongoImpl.class.getName());

  private Jongo getConnection() {
    if (jongo == null) {
      jongo = JongoConnectionFactory.getInstance().get();
    }
    return jongo;
  }

  private MongoCollection getUsersCollection() {
    MongoCollection collection = getConnection().getCollection(USER_COLLECTION);
    collection.ensureIndex("{uname : 1}", "{ unique: true}");
    return collection;
  }

  @Override
  public DatagateUser getUserByUsername(String username) {
    return getUsersCollection().findOne("{uname:#}", username).as(DatagateUser.class);
  }

  @Override
  public List<DatagateUser> getAllUsers() {
    ArrayList<DatagateUser> users = new ArrayList<>();
    getUsersCollection().find().as(DatagateUser.class).forEach(user -> users.add(user));
    return users;
  }

  @Override
  public List<DatagateUser> getExpiredUsers() {
    ArrayList<DatagateUser> users = new ArrayList<>();
    getUsersCollection().find("{$and:[{canExpire:true},{expiryDate:{$lte:#}}]}", new Date()).as(DatagateUser.class).forEach(user -> users.add(user));
    return users;
  }

  @Override
  public boolean addUser(DatagateUser user) {
    try {
      return getUsersCollection().insert(user).wasAcknowledged();
    } catch (DuplicateKeyException ex) {
      LOG.info(ex.getMessage());
      return false;
    }
  }

  @Override
  public void update(DatagateUser user) {
    getUsersCollection().update("{uname:#}", user.getUsername()).upsert().with(user);
  }

}
