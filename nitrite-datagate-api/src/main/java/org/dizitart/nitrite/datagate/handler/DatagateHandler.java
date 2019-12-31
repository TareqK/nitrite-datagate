/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.handler;

import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DatagateBusFactory;
import org.dizitart.nitrite.datagate.factory.DatagateServiceFactory;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcError;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;
import org.dizitart.nitrite.datagate.jsonrpc.request.AuthenticationRequest;
import org.dizitart.nitrite.datagate.jsonrpc.request.ChangeRequest;
import org.dizitart.nitrite.datagate.jsonrpc.request.ChangesSinceRequest;
import org.dizitart.nitrite.datagate.jsonrpc.response.AuthenticationResponse;
import org.dizitart.nitrite.datagate.jsonrpc.response.ChangeListResponse;
import org.dizitart.nitrite.datagate.service.DatagateService;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@Log
public abstract class DatagateHandler {

  private final HashMap<String, String> listeningCollections = new HashMap<>();
  private final HashMap<String, Object> attributes = new HashMap();

  private boolean authenticated = false;

  public void handleRequest(JsonRpcRequest request) throws IOException {
    JsonRpcResponse response = null;
    try {
      DatagateService dataGateService = DatagateServiceFactory.getInstance().get(this);
      if (!isAuthenticated() && StringUtils.equals(request.getMethod(), DatagateService.AUTHENTICATE)) {
        AuthenticationRequest authenticationRequest = request.getParamAs(AuthenticationRequest.class);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticated(dataGateService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        response = request.responseBuilder().result(authenticationResponse).build();
      } else {
        switch (request.getMethod()) {
          case DatagateService.AUTHENTICATE:
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAuthenticated(true);
            response = request.responseBuilder().result(authenticationResponse).build();
            break;
          case DatagateService.GET_COLLECTION_DATA:
            ChangesSinceRequest getCollectionRequest = request.getParamAs(ChangesSinceRequest.class);
            ChangeListResponse getCollectionResponse = new ChangeListResponse();
            getCollectionResponse.setChangeList(dataGateService.getCollection(getCollectionRequest.getCollection()));
            response = request.responseBuilder().result(getCollectionResponse).build();
            break;
          case DatagateService.GET_CHANGES_SINCE:
            ChangesSinceRequest changesSinceRequest = request.getParamAs(ChangesSinceRequest.class);
            ChangeListResponse changesSinceResponse = new ChangeListResponse();
            changesSinceResponse.setChangeList(dataGateService.getChangesSince(changesSinceRequest.getCollection(), changesSinceRequest.getTimestamp()));
            response = request.responseBuilder().result(changesSinceResponse)
             .build();
            break;
          case DatagateService.CHANGE:
            ChangeRequest changeRequest = request.getParamAs(ChangeRequest.class);
            dataGateService.change(changeRequest.getChangeList());
            response = request.responseBuilder().build();
            break;
          case DatagateService.SUBSCRIBE:
            ChangesSinceRequest subscribeRequest = request.getParamAs(ChangesSinceRequest.class);
            dataGateService.subscribe(subscribeRequest.getCollection(), request.getId());

            response = request.responseBuilder().build();
            break;
          case DatagateService.UNSUBSCRIBE:
            ChangesSinceRequest unsubscribeRequest = request.getParamAs(ChangesSinceRequest.class);
            dataGateService.unsubscribe(unsubscribeRequest.getCollection());
            response = request.responseBuilder().build();
            break;
          default:
            response = request.responseBuilder().error(JsonRpcError.METHOD_NOT_FOUND).build();
        }
      }
    } catch (IOException ex) {
      log.severe(ex.getMessage());
    }
    pushResponse(response);

  }

  public void unsubscribeFromBus() {
    DatagateBusFactory.getInstance().get().unsubscribe(this);
  }

  public void subscribeToBus() {
    DatagateBusFactory.getInstance().get().subscribe(this);
  }

  public void subscribeToCollection(String collectionName, String listener) {
    listeningCollections.put(collectionName, listener);
  }

  public void unsubscribeFromCollection(String collectionName) {
    listeningCollections.remove(collectionName);
  }

  public void pushChangeList(ChangeList changeList) {
    if (this.listeningCollections.containsKey(changeList.getCollection())) {
      pushResponse(JsonRpcResponse.builder().id(this.listeningCollections.get(changeList.getCollection())).result(changeList).build());
    }
  }

  public void setUser(String username) {
    this.getAttributes().put("username", username);
  }

  public String getUser() {
    return String.valueOf(getAttributes().get("username"));
  }

  protected abstract void pushResponse(JsonRpcResponse response);
}
