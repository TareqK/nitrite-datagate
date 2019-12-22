/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.auth;

/**
 *
 * @author tareq
 */
public class DataGateAuthenticatorDefaultImpl extends DataGateAuthenticator {

    @Override
    boolean doAuthentication(String username, String password) {
        return true;
    }
    
}
