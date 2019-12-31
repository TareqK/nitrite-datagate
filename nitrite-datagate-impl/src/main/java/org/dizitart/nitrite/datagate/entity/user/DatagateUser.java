/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author tareq
 */
@Getter
@Setter
public class DatagateUser {

  @Getter(onMethod_ = {
    @JsonProperty("uname")})
  @Setter(onMethod_ = {
    @JsonProperty("uname")})
  private String username;
  private String password;
  private boolean accountExpired = false;
  private Date creationTimeStamp;
  private Date expiryDate;
  private boolean canExpire = false;
  private boolean passwordExpired = false;
  private Role role = Role.ROLE_ADMIN;
}
