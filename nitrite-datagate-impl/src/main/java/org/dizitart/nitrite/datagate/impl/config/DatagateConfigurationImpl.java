/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.config;

import org.dizitart.nitrite.datagate.config.DatagateConfiguration;
import org.dizitart.nitrite.datagate.impl.auth.DatagateAuthenticatorJongoImpl;
import org.dizitart.nitrite.datagate.impl.bus.DatagateBusMapImpl;
import org.dizitart.nitrite.datagate.impl.repository.DatagateChangeListRepositoryJongoImpl;

/**
 *
 * @author tareq
 */
public class DatagateConfigurationImpl extends DatagateConfiguration {

  @Override
  public void configure() {
    setAuthenticator(DatagateAuthenticatorJongoImpl.class);
    setBus(DatagateBusMapImpl.class);
    setChangeListRepository(DatagateChangeListRepositoryJongoImpl.class);
  }

}
