/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.javaee.handler;

import java.io.IOException;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dizitart.nitrite.datagate.handler.DataGateHandler;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;
import org.dizitart.nitrite.datagate.javaee.decoder.JsonRpcDecoder;
import org.dizitart.nitrite.datagate.javaee.encoder.JsonRpcEncoder;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@ServerEndpoint(value = "/datagate",
 decoders = JsonRpcDecoder.class,
 encoders = JsonRpcEncoder.class)
@NoArgsConstructor
public class DatagateJsr356HandlerImpl extends DataGateHandler {

  private static final Logger LOG = Logger.getLogger(DatagateJsr356HandlerImpl.class.getName());
  Session socketSession;

  @OnOpen
  public void onOpen(Session session) throws IOException {
    this.socketSession = session;

  }

  @OnMessage
  public void onMessage(Session session, JsonRpcRequest request) throws IOException {
    this.handleRequest(request);
  }

  @OnClose
  public void onClose(Session session) throws IOException {
    this.unsubscribeFromBus();
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    LOG.severe(throwable.getMessage());
  }

  @Override
  protected void pushResponse(JsonRpcResponse response) {
    this.socketSession.getAsyncRemote().sendObject(response);
  }

}
