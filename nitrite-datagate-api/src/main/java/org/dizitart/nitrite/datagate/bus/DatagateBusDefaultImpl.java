/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.bus;

import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.handler.DatagateHandler;

/**
 *
 * @author tareq
 */
public class DatagateBusDefaultImpl implements DatagateBus {

  @Override
  public void subscribe(DatagateHandler session) {
  }

  @Override
  public void unsubscribe(DatagateHandler session) {
  }

  @Override
  public void publish(ChangeList changeList, String username) {
  }

}
