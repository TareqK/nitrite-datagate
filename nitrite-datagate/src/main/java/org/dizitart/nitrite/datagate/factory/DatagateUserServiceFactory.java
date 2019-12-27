/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.dizitart.nitrite.datagate.service.DatagateUserService;
import org.dizitart.nitrite.datagate.service.DatagateUserServiceImpl;

/**
 *
 * @author tareq
 */
public class DatagateUserServiceFactory {

  private static DatagateUserServiceFactory instance = getInstance();

  private DatagateUserServiceFactory() {

  }

  public static final DatagateUserServiceFactory getInstance() {
    if (instance == null) {
      instance = new DatagateUserServiceFactory();
    }
    return instance;
  }

  public DatagateUserService get() {
    return new DatagateUserServiceImpl();
  }

}
