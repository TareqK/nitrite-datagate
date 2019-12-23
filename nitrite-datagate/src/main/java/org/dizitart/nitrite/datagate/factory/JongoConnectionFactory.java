/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import org.jongo.Jongo;

/**
 *
 * @author tareq
 */
public class JongoConnectionFactory {

  private static JongoConnectionFactory instance = getInstance();

  private JongoConnectionFactory() {

  }

  public static final JongoConnectionFactory getInstance() {
    if (instance == null) {
      instance = new JongoConnectionFactory();
    }
    return instance;
  }

  public Jongo get() {
    return new Jongo(MongoConnectionFactory.getInstance().get());
  }

}
