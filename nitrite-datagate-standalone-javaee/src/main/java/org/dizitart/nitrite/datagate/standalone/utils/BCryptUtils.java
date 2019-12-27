/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.standalone.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author tareq
 */
public class BCryptUtils {

  private BCryptUtils() {
    throw new IllegalArgumentException("This is a Utility Class");
  }

  /**
   * Hashes a password
   *
   * @param password the password to hash
   * @return the hashed password
   */
  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Validates a plaintext password vs a hashed one
   *
   * @param hashedPassword the hashedPassword to check against
   * @param password the password to check
   * @return true if the passwords are the same, false otherwise
   */
  public static boolean validatePassword(String hashedPassword, String password) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}
