/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;

/**
 *
 * @author tareq
 */
public class MongoConnectionFactory {

  private static MongoConnectionFactory instance = getInstance();
  private MongoClient mongoClient;

  private String database = "datagate";
  private String authDatabase = "datagate-users";
  private String host = "localhost";
  private String port = "27017";
  private String username = null;
  private String password = null;
  private boolean sslEnabled = false;

  public static final MongoConnectionFactory getInstance() {
    if (instance == null) {
      instance = new MongoConnectionFactory();
    }
    return instance;
  }

  private void connect() {
    if (mongoClient != null) {
      mongoClient.close();
    }
    MongoClientOptions options = MongoClientOptions.builder().sslEnabled(sslEnabled).build();
    if (username != null && authDatabase != null && password != null) {
      MongoCredential credential = MongoCredential.createCredential(username, authDatabase, password.toCharArray());
      mongoClient = new MongoClient(new ServerAddress(host, Integer.parseInt(port)),
       Arrays.asList(credential),
       options);
    } else {
      mongoClient = new MongoClient(new ServerAddress(host, Integer.parseInt(port)), options);
    }
  }

  public DB get() {
    if (mongoClient == null) {
      connect();
    }
    return mongoClient.getDB(database);
  }

  public void setDatabase(String newDatabase) {
    this.database = newDatabase;
    connect();
  }

  public String getAuthDatabase() {
    return authDatabase;
  }

  public void setAuthDatabase(String authDatabase) {
    this.authDatabase = authDatabase;
    connect();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
    connect();
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
    connect();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
    connect();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
    connect();
  }

  public boolean isSslEnabled() {
    return sslEnabled;
  }

  public void setSslEnabled(boolean sslEnabled) {
    this.sslEnabled = sslEnabled;
    connect();
  }

}
