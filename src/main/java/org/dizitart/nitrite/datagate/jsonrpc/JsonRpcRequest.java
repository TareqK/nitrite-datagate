/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
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
public class JsonRpcRequest {
    
    private final String jsonrpc = "2.0";
    private String method;
    private Map<String,String> params;
    private String id;
    public <T> T getParamAs(String name, Class<T> clazz) throws IOException{
        return new ObjectMapper().readValue(params.get(name),clazz);
    }
    
    public JsonRpcResponseBuilder responseBuilder(){
        return JsonRpcResponse.builder().id(id);
    }
}
