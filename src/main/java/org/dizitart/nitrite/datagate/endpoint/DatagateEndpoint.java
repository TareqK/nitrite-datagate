/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.endpoint;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCode;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.HeaderParam;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import org.dizitart.nitrite.datagate.config.DataGateConfiguration;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.factory.DataGateServiceFactory;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcError;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcRequest;
import org.dizitart.nitrite.datagate.jsonrpc.JsonRpcResponse;
import org.dizitart.nitrite.datagate.jsonrpc.decoder.JsonRpcDecoder;
import org.dizitart.nitrite.datagate.jsonrpc.encoder.JsonRpcEncoder;
import org.dizitart.nitrite.datagate.session.DataGateSessionManager;
import org.dizitart.nitrite.datagate.session.DataGateSessionManagerImpl;
import org.dizitart.nitrite.datagate.service.DataGateService;

/**
 *
 * @author tareq
 */
@ServerEndpoint(value = "/datagate",
        decoders = JsonRpcDecoder.class,
        encoders = JsonRpcEncoder.class)
public class DatagateEndpoint {

    private static final Logger LOG = Logger.getLogger(DatagateEndpoint.class.getName());
    
    @OnOpen
    public void onOpen(Session session, @HeaderParam(AUTHORIZATION) String authorizationHeader) throws IOException {
        boolean isAuth = DataGateConfiguration.getInstance().authenticator().authenticate(session, authorizationHeader);
        if (isAuth) {
            DataGateSessionManagerImpl.getInstance().addSession(session);
        } else {
            session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "This session is not authenticated"));
        }
    }

    @OnMessage
    public void onMessage(Session session, JsonRpcRequest request) throws IOException {
        DataGateService dataGateService = DataGateServiceFactory.getInstance().get(session);
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
                dataGateService.change(request.getParamAs("changeList", ChangeList.class),
                        request.getParamAs("collection", String.class));
                response = request.responseBuilder().build();
                break;
            default:
                response = request.responseBuilder().error(JsonRpcError.METHOD_NOT_FOUND).build();
        }
        session.getAsyncRemote().sendObject(response);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        DataGateSessionManagerImpl.getInstance().removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
       LOG.severe(throwable.getMessage());
    }
}
