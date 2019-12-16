/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;

/**
 *
 * @author tareq
 */
public class JsonRpcDecoder implements Decoder.Text<JsonRpcRequest>{

    @Override
    public JsonRpcRequest decode(String arg0) throws DecodeException {
        try {
            return new ObjectMapper().readValue(arg0, JsonRpcRequest.class);
        } catch (IOException ex) {
             throw new DecodeException(arg0,"Failed to Decode",ex);
        }
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig arg0) {
    }

    @Override
    public void destroy() {
    }
    
}