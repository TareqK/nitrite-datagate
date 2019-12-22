/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.dizitart.nitrite.datagate.factory.DataGateBusFactory;
import org.dizitart.nitrite.datagate.factory.DataGateServiceFactory;
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
  private static final String UPDATE = "update";
  private final List<String> collections = new ArrayList<>();
  private Session session;
  private boolean authenticated = false;

  @OnOpen
  public void onOpen(Session session) throws IOException {
    this.session = session;
    DataGateBusFactory.getInstance().get().subscribe(this);
  }

  @OnMessage
  public void onMessage(Session session, JsonRpcRequest request) throws IOException {
    DataGateService dataGateService = DataGateServiceFactory.getInstance().get(this);
    JsonRpcResponse response = null;
    if (!authenticated && StringUtils.equals(request.getMethod(), DataGateService.AUTHENTICATE)) {
      AuthenticationRequest authenticationRequest = request.getParamAs(AuthenticationRequest.class);
      AuthenticationResponse authenticationResponse = new AuthenticationResponse();
      authenticationResponse.setAuthenticated(dataGateService.authenticate(this, authenticationRequest.getUsername(), authenticationRequest.getPassword()));
      response = request.responseBuilder().result(authenticationResponse).build();
    } else {
      switch (request.getMethod()) {
        case DataGateService.GET_COLLECTION:
          ChangesSinceRequest getCollectionRequest = request.getParamAs(ChangesSinceRequest.class);
          ChangeListResponse getCollectionResponse = new ChangeListResponse();
          getCollectionResponse.setChangeList(dataGateService.getCollection(getCollectionRequest.getCollectionName()));
          response = request.responseBuilder().result(getCollectionResponse).build();
          break;
        case DataGateService.GET_CHANGES_SINCE:
          ChangesSinceRequest changesSinceRequest = request.getParamAs(ChangesSinceRequest.class);
          ChangeListResponse changesSinceResponse = new ChangeListResponse();
          changesSinceResponse.setChangeList(dataGateService.getChangesSince(changesSinceRequest.getCollectionName(), changesSinceRequest.getTimeStamp()));
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
          dataGateService.subscribe(this, subscribeRequest.getCollectionName());
          response = request.responseBuilder().build();
          break;
        default:
          response = request.responseBuilder().error(JsonRpcError.METHOD_NOT_FOUND).build();
      }
    }

    session.getAsyncRemote().sendObject(response);
  }

  @OnClose
  public void onClose(Session session) throws IOException {
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    LOG.severe(throwable.getMessage());
  }

  public void listenToCollection(String collectionName) {
    collections.add(collectionName);
  }

  public void push(ChangeList changeList) {
    if (this.collections.contains(changeList.getCollection())) {
      this.session.getAsyncRemote().sendObject(JsonRpcResponse.builder().id(UPDATE).result(session).build());
    }

  }

  public Session getSession() {
    return this.session;
  }

  public String getSessionUser() {
    return String.valueOf(session.getUserProperties().get("username"));
  }
}
