/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.config;

import org.dizitart.nitrite.datagate.auth.DatagateAuthenticatorDefaultImpl;
import org.dizitart.nitrite.datagate.bus.DatagateBusDefaultImpl;
import org.dizitart.nitrite.datagate.repository.DatagateChangeListRepositoryDefaultImpl;

/**
 *
 * @author tareq
 */
public class DatagateConfigurationDefaultImpl extends DatagateConfiguration {

  @Override
  public void configure() {
    setAuthenticator(DatagateAuthenticatorDefaultImpl.class);
    setBus(DatagateBusDefaultImpl.class);
    setChangeListRepository(DatagateChangeListRepositoryDefaultImpl.class);
  }

}
