/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author tareq
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {

  private String username;
  private String password;
}
