/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.service;

import java.util.Date;
import org.dizitart.nitrite.datagate.entity.user.DatagateUser;
import org.dizitart.nitrite.datagate.entity.user.Role;
import org.dizitart.nitrite.datagate.impl.factory.DatagateUserRepositoryFactory;
import org.dizitart.nitrite.datagate.impl.repository.DatagateUserRepository;
import org.dizitart.nitrite.datagate.impl.utils.BCryptUtils;

/**
 *
 * @author tareq
 */
public class DatagateUserServiceImpl implements DatagateUserService {

  private final DatagateUserRepository userRepository = DatagateUserRepositoryFactory.getInstance().get();

  @Override
  public boolean authenticate(String username, String password) {
    DatagateUser foundUser = userRepository.getUserByUsername(username);
    return BCryptUtils.validatePassword(foundUser.getPassword(), password);
  }

  @Override
  public boolean changePassword(String username, String oldPassword, String newPassword) {
    DatagateUser foundUser = userRepository.getUserByUsername(username);
    if (foundUser != null && BCryptUtils.validatePassword(foundUser.getPassword(), oldPassword)) {
      foundUser.setPassword(BCryptUtils.hashPassword(newPassword));
      foundUser.setPasswordExpired(false);
      userRepository.update(foundUser);
      return true;
    }
    return false;
  }

  @Override
  public void setAccountExpiryDate(String username, Date expiryDate) {
    DatagateUser foundUser = userRepository.getUserByUsername(username);
    if (foundUser != null) {
      if (expiryDate != null) {
        foundUser.setExpiryDate(expiryDate);
        foundUser.setAccountExpired(false);
        foundUser.setCanExpire(true);
        userRepository.update(foundUser);
      } else {
        foundUser.setAccountExpired(true);
        foundUser.setCanExpire(false);
        userRepository.update(foundUser);
      }
    }
  }

  @Override
  public DatagateUser createUser(String username, String password, Role role) {
    DatagateUser user = new DatagateUser();
    user.setCreationTimeStamp(new Date());
    user.setUsername(username);
    user.setPassword(BCryptUtils.hashPassword(password));
    user.setRole(role);
    userRepository.addUser(user);
    return user;
  }

  @Override
  public void expirePassword(String username) {
    DatagateUser foundUser = userRepository.getUserByUsername(username);
    foundUser.setPasswordExpired(true);
    userRepository.update(foundUser);
  }

  @Override
  public void expireAccount(String username) {
    DatagateUser foundUser = userRepository.getUserByUsername(username);
    foundUser.setCanExpire(true);
    foundUser.setAccountExpired(true);
    userRepository.update(foundUser);
  }

}
