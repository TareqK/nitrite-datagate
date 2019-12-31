/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.repository;

import java.util.List;
import org.dizitart.nitrite.datagate.entity.user.DatagateUser;

/**
 *
 * @author tareq
 */
public interface DatagateUserRepository {

  /**
   * Gets a user by the username
   *
   * @param username the username to look for
   * @return the user if found, null otherwise
   */
  DatagateUser getUserByUsername(String username);

  /**
   * Gets a list of all users
   *
   * @return a list of users
   */
  List<DatagateUser> getAllUsers();

  /**
   * Get a list of all expired users
   *
   * @return a list of expired users
   */
  List<DatagateUser> getExpiredUsers();

  /**
   * Add a new user
   *
   * @param user the user to add
   * @return true if the user was added successfully, false otherwise
   */
  boolean addUser(DatagateUser user);

  /**
   * Update an existing user by matching the username
   *
   * @param user the user to update
   */
  void update(DatagateUser user);
}
