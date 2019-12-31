/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.config;

import lombok.Getter;
import lombok.Setter;
import org.dizitart.nitrite.datagate.auth.DatagateAuthenticator;
import org.dizitart.nitrite.datagate.bus.DatagateBus;
import org.dizitart.nitrite.datagate.repository.DatagateChangeListRepository;

/**
 *
 * @author tareq
 */
@Getter
@Setter
public abstract class DatagateConfiguration {

  private Class< ? extends DatagateAuthenticator> authenticator;
  private Class< ? extends DatagateBus> bus;
  private Class< ? extends DatagateChangeListRepository> changeListRepository;

  public abstract void configure();

}
