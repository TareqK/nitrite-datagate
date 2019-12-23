/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.session;

import java.io.IOException;
import java.util.HashMap;
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
import org.apache.commons.lang3.StringUtils;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DatagateBusFactory;
import org.dizitart.nitrite.datagate.factory.DatagateServiceFactory;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcError;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;
import org.dizitart.nitrite.datagate.jsonrpc.decoder.JsonRpcDecoder;
import org.dizitart.nitrite.datagate.jsonrpc.encoder.JsonRpcEncoder;
import org.dizitart.nitrite.datagate.request.AuthenticationRequest;
import org.dizitart.nitrite.datagate.request.ChangeRequest;
import org.dizitart.nitrite.datagate.request.ChangesSinceRequest;
import org.dizitart.nitrite.datagate.response.AuthenticationResponse;
import org.dizitart.nitrite.datagate.response.ChangeListResponse;
import org.dizitart.nitrite.datagate.service.DataGateService;

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
public class DataGateSession {

  private static final Logger LOG = Logger.getLogger(DataGateSession.class.getName());

  private final HashMap<String, String> listeningCollections = new HashMap<>();
  private Session socketSession;
  private boolean authenticated = false;

  @OnOpen
  public void onOpen(Session session) throws IOException {
    this.socketSession = session;

  }

  @OnMessage
  public void onMessage(Session session, JsonRpcRequest request) throws IOException {
    try {
      DataGateService dataGateService = DatagateServiceFactory.getInstance().get(this);
      JsonRpcResponse response = null;
      if (!authenticated && StringUtils.equals(request.getMethod(), DataGateService.AUTHENTICATE)) {
        AuthenticationRequest authenticationRequest = request.getParamAs(AuthenticationRequest.class);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticated(dataGateService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        DatagateBusFactory.getInstance().get().subscribe(this);
        response = request.responseBuilder().result(authenticationResponse).build();
      } else {
        switch (request.getMethod()) {
          case DataGateService.GET_COLLECTION_DATA:
            ChangesSinceRequest getCollectionRequest = request.getParamAs(ChangesSinceRequest.class);
            ChangeListResponse getCollectionResponse = new ChangeListResponse();
            getCollectionResponse.setChangeList(dataGateService.getCollection(getCollectionRequest.getCollection()));
            response = request.responseBuilder().result(getCollectionResponse).build();
            break;
          case DataGateService.GET_CHANGES_SINCE:
            ChangesSinceRequest changesSinceRequest = request.getParamAs(ChangesSinceRequest.class);
            ChangeListResponse changesSinceResponse = new ChangeListResponse();
            changesSinceResponse.setChangeList(dataGateService.getChangesSince(changesSinceRequest.getCollection(), changesSinceRequest.getTimestamp()));
            response = request.responseBuilder().result(changesSinceResponse)
             .build();
            break;
          case DataGateService.CHANGE:
            ChangeRequest changeRequest = request.getParamAs(ChangeRequest.class);
            dataGateService.change(changeRequest.getChangeList());
            response = request.responseBuilder().build();
            break;
          case DataGateService.SUBSCRIBE:
            ChangesSinceRequest subscribeRequest = request.getParamAs(ChangesSinceRequest.class);
            dataGateService.subscribe(subscribeRequest.getCollection(), request.getId());

            response = request.responseBuilder().build();
            break;
          case DataGateService.UNSUBSCRIBE:
            ChangesSinceRequest unsubscribeRequest = request.getParamAs(ChangesSinceRequest.class);
            dataGateService.unsubscribe(unsubscribeRequest.getCollection());
            response = request.responseBuilder().build();
            break;
          default:
            response = request.responseBuilder().error(JsonRpcError.METHOD_NOT_FOUND).build();
        }
      }
      session.getAsyncRemote().sendObject(response);
    } catch (IOException ex) {
      LOG.severe(ex.getMessage());
    }
  }

  @OnClose
  public void onClose(Session session) throws IOException {
    DatagateBusFactory.getInstance().get().unsubscribe(this);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    LOG.severe(throwable.getMessage());
  }

  public void subscribeToCollection(String collectionName, String listener) {
    listeningCollections.put(collectionName, listener);
  }

  public void pushChangeList(ChangeList changeList) {
    if (this.listeningCollections.containsKey(changeList.getCollection())) {
      this.socketSession.getAsyncRemote().sendObject(JsonRpcResponse.builder().id(this.listeningCollections.get(changeList.getCollection())).result(changeList).build());
    }

  }

  public String getSessionUser() {
    return String.valueOf(socketSession.getUserProperties().get("username"));
  }

  public void unsubscribeFromCollection(String collectionName) {
    listeningCollections.remove(collectionName);
  }
}
