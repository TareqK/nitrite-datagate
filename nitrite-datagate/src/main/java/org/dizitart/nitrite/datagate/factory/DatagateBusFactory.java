/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.bus.DataGateBus;
import org.dizitart.nitrite.datagate.bus.DataGateBusDefaultImpl;

/**
 *
 * @author tareq
 */
public class DatagateBusFactory {

  private static DatagateBusFactory instance = getInstance();
  private DataGateBus bus = new DataGateBusDefaultImpl();
  private static final Logger LOG = Logger.getLogger(DatagateBusFactory.class.getName());

  private DatagateBusFactory() {

  }

  public static final DatagateBusFactory getInstance() {
    if (instance == null) {
      instance = new DatagateBusFactory();
    }
    return instance;
  }

  public DataGateBus get() {
    return bus;
  }

  public void setBus(Class<? extends DataGateBus> clazz) {
    try {
      this.bus = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
      LOG.severe(ex.getMessage());
      LOG.info("Bus class not found, defaulting to default bus");
      this.bus = new DataGateBusDefaultImpl();
    }
  }

}
