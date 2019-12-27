/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.standalone.service;

import java.util.Date;
import org.dizitart.nitrite.datagate.standalone.entity.user.DatagateUser;
import org.dizitart.nitrite.datagate.standalone.entity.user.Role;

/**
 *
 * @author tareq
 */
public interface DatagateUserService {

  /**
   * Attempts to authenticate the user with these credentials
   *
   * @param username the username to authenticate
   * @param password the password to authenticate with
   * @return true if authenticated, false otherwise
   */
  boolean authenticate(String username, String password);

  /**
   * Attempts to change the password of a user
   *
   * @param username the username to change the password for
   * @param oldPassword the old password
   * @param newPassword the new password
   * @return true if the password was changed, false otherwise
   */
  boolean changePassword(String username, String oldPassword, String newPassword);

  /**
   * sets the expiry date for the accounts
   *
   * @param username the account to set the expiry date for
   * @param expiryDate the expiry date. If null, sets the account to non-expirable
   */
  void setAccountExpiryDate(String username, Date expiryDate);

  /**
   * Creates a new user with this role
   *
   * @param username the username of the user
   * @param password the password of the user
   * @param role the role of the user
   * @return the created user
   */
  DatagateUser createUser(String username, String password, Role role);

  /**
   * Expire's a user's password, asking them to change it
   *
   * @param username the username whose password to expire
   */
  void expirePassword(String username);

  /**
   * Expire's a user's account, disabling login
   *
   * @param username the username whose account we are expiring
   */
  void expireAccount(String username);

}
