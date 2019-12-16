/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc.encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;

/**
 *
 * @author tareq
 */
public class JsonRpcEncoder implements Encoder.Text<JsonRpcResponse> {

    @Override
    public String encode(JsonRpcResponse arg0) throws EncodeException {
        try {
            return new ObjectMapper().writeValueAsString(arg0);
        } catch (JsonProcessingException ex) {
            throw new EncodeException(arg0,"Failed to Encode",ex);
            
        }
    }

    @Override
    public void init(EndpointConfig arg0) {
    }

    @Override
    public void destroy() {
    }

    
}
