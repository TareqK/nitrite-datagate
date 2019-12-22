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
public class DataGateServiceFactory {

  private static DataGateServiceFactory instance = getInstance();

  private DataGateServiceFactory() {

  }

  public static DataGateServiceFactory getInstance() {
    if (instance == null) {
      instance = new DataGateServiceFactory();
    }
    return instance;
  }

  public DataGateService get(DataGateSession session) {
    DataGateService service = new DataGateService();
    service.setSession(session);
    return service;
  }

}
