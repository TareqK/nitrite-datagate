/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.util.logging.Logger;
import lombok.SneakyThrows;
import org.dizitart.nitrite.datagate.bus.DatagateBus;
import org.dizitart.nitrite.datagate.config.Datagate;

/**
 *
 * @author tareq
 */
public class DatagateBusFactory {

  private static DatagateBusFactory instance = getInstance();
  private DatagateBus bus;
  private static final Logger LOG = Logger.getLogger(DatagateBusFactory.class.getName());

  @SneakyThrows(Exception.class)
  private DatagateBusFactory() {
    bus = Datagate.getConfiguration().getBus().getDeclaredConstructor().newInstance();
  }

  public static final DatagateBusFactory getInstance() {
    if (instance == null) {
      instance = new DatagateBusFactory();
    }
    return instance;
  }

  public DatagateBus get() {
    return bus;
  }

}
