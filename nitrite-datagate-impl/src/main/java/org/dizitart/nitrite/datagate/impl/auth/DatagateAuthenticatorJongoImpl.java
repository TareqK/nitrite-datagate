/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.auth;

import org.dizitart.nitrite.datagate.auth.DatagateAuthenticator;
import org.dizitart.nitrite.datagate.impl.factory.DatagateUserServiceFactory;
import org.dizitart.nitrite.datagate.impl.service.DatagateUserService;

/**
 *
 * @author tareq
 */
public class DatagateAuthenticatorJongoImpl extends DatagateAuthenticator {

  private final DatagateUserService service = DatagateUserServiceFactory.getInstance().get();

  @Override
  protected boolean doAuthentication(String username, String password) {
    return service.authenticate(username, password);
  }

}
