/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.config;

import lombok.Getter;

/**
 *
 * @author tareq
 */
public class Datagate {

  @Getter
  private static DatagateConfiguration configuration;

  public static void setConfiguration(DatagateConfiguration newConfig) {
    configuration = newConfig;
    configuration.configure();
  }
}
