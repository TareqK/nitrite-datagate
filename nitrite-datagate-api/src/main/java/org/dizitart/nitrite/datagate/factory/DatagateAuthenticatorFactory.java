/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.util.logging.Logger;
import lombok.SneakyThrows;
import org.dizitart.nitrite.datagate.auth.DatagateAuthenticator;
import org.dizitart.nitrite.datagate.config.Datagate;

/**
 *
 * @author tareq
 */
public class DatagateAuthenticatorFactory {

  private static DatagateAuthenticatorFactory instance = getInstance();
  private static final Logger LOG = Logger.getLogger(DatagateAuthenticatorFactory.class.getName());

  private DatagateAuthenticator authenticator;

  @SneakyThrows(Exception.class)
  private DatagateAuthenticatorFactory() {
    authenticator = Datagate.getConfiguration().getAuthenticator().getDeclaredConstructor().newInstance();
  }

  public static DatagateAuthenticatorFactory getInstance() {
    if (instance == null) {
      instance = new DatagateAuthenticatorFactory();
    }
    return instance;
  }

  public DatagateAuthenticator get() {
    return authenticator;
  }

}
