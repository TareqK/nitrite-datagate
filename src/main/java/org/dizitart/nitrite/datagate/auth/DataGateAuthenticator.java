package org.dizitart.nitrite.datagate.auth;

import java.util.Base64;
import java.util.logging.Logger;
import javax.websocket.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
/**
 *
 * @author tareq
 */
public abstract class DataGateAuthenticator {

    private static final String BASIC = "Basic";
    private static final Logger LOG = Logger.getLogger(DataGateAuthenticator.class.getName());

    public boolean authenticate(Session session, String authorization) {

        try {
            String base64 = authorization.substring(authorization.lastIndexOf(BASIC) + 1).strip();
            String decoded = new String(Base64.getDecoder().decode(base64));
            String[] credentials = decoded.split(":");
            String username = credentials[0];
            String password = credentials[1];
            session.getUserProperties().put("username", username);
            return doAuthentication(username, password);
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
            return false;
        }
    }

    abstract boolean doAuthentication(String username, String password);
}
