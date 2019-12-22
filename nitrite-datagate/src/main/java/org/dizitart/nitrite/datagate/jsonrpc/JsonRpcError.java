/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class JsonRpcError {

  public JsonRpcError() {

  }
  private Integer code;
  private String message;
  private Object data;

  public static final JsonRpcError METHOD_NOT_FOUND = JsonRpcError.builder().code(-32601).message("Method Not Found").build();

}
