/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.jsonrpc;

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
public class JsonRpcResponse {
    
    private final String jsonrpc ="2.0";
    private Object result;
    private JsonRpcError error;
    private String id;
}
