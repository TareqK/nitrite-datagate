/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.config.Datagate;
import org.dizitart.nitrite.datagate.repository.DatagateChangeListRepository;
import org.dizitart.nitrite.datagate.repository.DatagateChangeListRepositoryDefaultImpl;

/**
 *
 * @author tareq
 */
public class DatagateChangeListRepositoryFactory {

  private static DatagateChangeListRepositoryFactory instance = getInstance();
  private Class<? extends DatagateChangeListRepository> repositoryClazz;
  private static final Logger LOG = Logger.getLogger(DatagateChangeListRepositoryFactory.class.getName());

  private DatagateChangeListRepositoryFactory() {
    repositoryClazz = Datagate.getConfiguration().getChangeListRepository();
  }

  public static final DatagateChangeListRepositoryFactory getInstance() {
    if (instance == null) {
      instance = new DatagateChangeListRepositoryFactory();
    }
    return instance;
  }

  public DatagateChangeListRepository get(String username) {
    try {
      DatagateChangeListRepository repo = repositoryClazz.getDeclaredConstructor().newInstance();
      repo.setUser(username);
      return repo;
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
      LOG.severe(ex.getMessage());
      LOG.info("Repository class not found, defaulting to default repository");
      DatagateChangeListRepository repo = new DatagateChangeListRepositoryDefaultImpl();
      repo.setUser(username);
      return repo;
    }
  }

}
