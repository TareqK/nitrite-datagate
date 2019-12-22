/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse.JsonRpcResponseBuilder;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class JsonRpcRequest {

  public JsonRpcRequest() {

  }
  private final String jsonrpc = "2.0";
  private String method;
  private Object params;
  private String id;

  public <T> T getParamAs(Class<T> clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String raw = mapper.writeValueAsString(params);
    return new ObjectMapper().readValue(raw, clazz);
  }

  public JsonRpcResponseBuilder responseBuilder() {
    return JsonRpcResponse.builder().id(id);
  }
}
