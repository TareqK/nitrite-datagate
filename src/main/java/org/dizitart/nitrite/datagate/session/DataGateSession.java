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
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.HeaderParam;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DataGateAuthenticatorFactory;
import org.dizitart.nitrite.datagate.factory.DataGateBusFactory;
import org.dizitart.nitrite.datagate.factory.DataGateServiceFactory;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcError;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;
import org.dizitart.nitrite.datagate.jsonrpc.decoder.JsonRpcDecoder;
import org.dizitart.nitrite.datagate.jsonrpc.encoder.JsonRpcEncoder;
import org.dizitart.nitrite.datagate.service.DataGateService;

/**
 *
 * @author tareq
 */
@ServerEndpoint(value = "/datagate",
        decoders = JsonRpcDecoder.class,
        encoders = JsonRpcEncoder.class)
public class DataGateSession {

    private static final Logger LOG = Logger.getLogger(DataGateSession.class.getName());
    private static final String UPDATE = "update";
    private final List<String> collections = new ArrayList<>();
    private Session session;
    
    @OnOpen
    public void onOpen(Session session, @HeaderParam(AUTHORIZATION) String authorizationHeader) throws IOException {
        boolean isAuth = DataGateAuthenticatorFactory.getInstance().get().authenticate(session, authorizationHeader);
        if (!isAuth) {
            session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "This session is not authenticated"));
        }
        this.session = session;
        DataGateBusFactory.getInstance().get().subscribe(this);
    }


    @OnMessage
    public void onMessage(Session session, JsonRpcRequest request) throws IOException {
        DataGateService dataGateService = DataGateServiceFactory.getInstance().get(this);
        JsonRpcResponse response = null ;
        switch(request.getMethod()){
            case DataGateService.GET_COLLECTION:
                response = request.responseBuilder().result(dataGateService.getCollection(request.getParamAs("collection", String.class))).build();
                break;
            case DataGateService.GET_CHANGES_SINCE:
                response = request.responseBuilder().result(
                        dataGateService.getChangesSince(
                                request.getParamAs("collection", String.class),
                                request.getParamAs("timestamp", Long.class)))
                        .build();
                break;
            case DataGateService.CHANGE:
                dataGateService.change(request.getParamAs("changeList", ChangeList.class));
                response = request.responseBuilder().build();
                break;
            case DataGateService.SUBSCRIBE:
                dataGateService.subscribe(this, request.getParamAs("collection", String.class));
                response = request.responseBuilder().build();
                break;
            default:
                response = request.responseBuilder().error(JsonRpcError.METHOD_NOT_FOUND).build();
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
    
    public void listenToCollection(String collectionName){
        collections.add(collectionName);
    }
    
    public void push(ChangeList changeList){
        if(this.collections.contains(changeList.getCollection())){
            this.session.getAsyncRemote().sendObject( JsonRpcResponse.builder().id(UPDATE).result(session).build());
        }
    
    }
    
    public Session getSession(){
        return this.session;
    }
    
   public String getSessionUser(){
       return String.valueOf(session.getUserProperties().get("username"));
   }
}
