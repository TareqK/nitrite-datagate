/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dizitart.nitrite.datagate.entity.ChangeList;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangeRequest {

  private ChangeList changeList;

}
