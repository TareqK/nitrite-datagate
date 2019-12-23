/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.dizitart.nitrite.datagate.service.DataGateService;
import org.dizitart.nitrite.datagate.session.DataGateSession;

/**
 *
 * @author tareq
 */
public class DatagateServiceFactory {

  private static DatagateServiceFactory instance = getInstance();

  private DatagateServiceFactory() {

  }

  public static DatagateServiceFactory getInstance() {
    if (instance == null) {
      instance = new DatagateServiceFactory();
    }
    return instance;
  }

  public DataGateService get(DataGateSession session) {
    DataGateService service = new DataGateService();
    service.setSession(session);
    return service;
  }

}
