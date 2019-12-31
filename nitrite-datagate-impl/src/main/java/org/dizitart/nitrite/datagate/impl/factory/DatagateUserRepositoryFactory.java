/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.factory;

import org.dizitart.nitrite.datagate.impl.repository.DatagateUserRepository;
import org.dizitart.nitrite.datagate.impl.repository.DatagateUserRepositoryDefaultImpl;

/**
 *
 * @author tareq
 */
public class DatagateUserRepositoryFactory {

  private static DatagateUserRepositoryFactory instance = getInstance();

  private DatagateUserRepositoryFactory() {

  }

  public static final DatagateUserRepositoryFactory getInstance() {
    if (instance == null) {
      instance = new DatagateUserRepositoryFactory();
    }
    return instance;
  }

  public DatagateUserRepository get() {
    return new DatagateUserRepositoryDefaultImpl();
  }

}
