/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.standalone.authentication;

import org.dizitart.nitrite.datagate.auth.DatagateAuthenticator;
import org.dizitart.nitrite.datagate.standalone.factory.DatagateUserServiceFactory;
import org.dizitart.nitrite.datagate.standalone.service.DatagateUserService;

/**
 *
 * @author tareq
 */
public class DatagateJongoAuthenticator extends DatagateAuthenticator {

  private final DatagateUserService service = DatagateUserServiceFactory.getInstance().get();

  @Override
  protected boolean doAuthentication(String username, String password) {
    return service.authenticate(username, password);
  }

}
