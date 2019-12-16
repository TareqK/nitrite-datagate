/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.config;

import org.dizitart.nitrite.datagate.auth.SocketAuthenticator;

/**
 *
 * @author tareq
 */
public class DataGateConfiguration {

    private static DataGateConfiguration instance = getInstance();
    private SocketAuthenticator socketAuthenticator;

    private DataGateConfiguration() {

    }

    public static final DataGateConfiguration getInstance() {
        if (instance == null) {
            instance = new DataGateConfiguration();
        }
        return instance;
    }

    public DataGateConfiguration authenticator(SocketAuthenticator socketAuthenticator) {
        this.socketAuthenticator = socketAuthenticator;
        return this;

    }

    public SocketAuthenticator authenticator() {
        return this.socketAuthenticator;
    }

}
